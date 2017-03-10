package com.animaker.view.builder.layer;

import com.animaker.model.Layer;
import com.animaker.model.Layer.LayerType;
import com.animaker.model.Slide;
import com.animaker.view.builder.slide.SlideControlBase;
import com.animaker.view.builder.Workbench;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lemmi on 21.12.16.
 */
public class LayersPaletteView extends SlideControlBase {

    private ListView<Layer> listView;

    public LayersPaletteView(Workbench workbench) {
        super(workbench);

        getStyleClass().add("palette");

        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(listView -> new LayerCell());
        listView.setFocusTraversable(false);
        listView.getSelectionModel().selectedItemProperty()
                .addListener(it -> setSelectedLayer(listView.getSelectionModel().getSelectedItem()));

        workbench.selectedLayerProperty().addListener(it -> {
            Layer layer = workbench.getSelectedLayer();
            if (layer != null) {
                listView.getSelectionModel().select(layer);
                listView.scrollTo(layer);
            }
        });


        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Node plusIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS);
        plusIcon.getStyleClass().add("palette-button");

        Button addLayerButton = new Button();
        addLayerButton.setOnAction(evt -> {
            Slide slide = getSlide();
            if (slide != null) {
                Layer layer = new Layer("Untitled");
                slide.getLayers().add(layer);
            }
        });

        addLayerButton.setGraphic(plusIcon);
        addLayerButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        hbox.getChildren().add(addLayerButton);

        Label title = new Label("Layers");
        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add("palette-title");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(listView);
        borderPane.setBottom(hbox);

        getChildren().add(borderPane);

        slideProperty().addListener(it -> updateView());
    }

    private final ObjectProperty<Layer> selectedLayer = new SimpleObjectProperty<>(this, "selectedLayer");

    public final ObjectProperty<Layer> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Layer selectedLayer) {
        this.selectedLayer.set(selectedLayer);
    }

    public final Layer getSelectedLayer() {
        return selectedLayer.get();
    }

    private void updateView() {
        Slide slide = getSlide();
        if (slide != null) {
            listView.setItems(slide.getLayers());
        } else {
            listView.setItems(FXCollections.emptyObservableList());
        }

        if (!listView.getItems().isEmpty()) {
            listView.getSelectionModel().select(0);
        }
    }

    class LayerCell extends ListCell<Layer> {

        private VBox vbox;
        private MenuButton typeButton = new MenuButton();
        private ToggleButton lockedButton = new ToggleButton();
        private ToggleButton visibleButton = new ToggleButton();
        private TextField nameField = new TextField();
        private Map<LayerType, RadioMenuItem> typeItemMap = new HashMap<>();

        public LayerCell() {
            Node eyeIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EYE);
            eyeIcon.getStyleClass().add("layer-button");

            visibleButton.setGraphic(eyeIcon);

            HBox.setHgrow(nameField, Priority.ALWAYS);
            HBox.setHgrow(visibleButton, Priority.NEVER);
            HBox.setHgrow(lockedButton, Priority.NEVER);
            HBox.setHgrow(typeButton, Priority.NEVER);

            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().setAll(typeButton, nameField, visibleButton, lockedButton);

            ToggleGroup group = new ToggleGroup();
            for (LayerType type : LayerType.values()) {
                RadioMenuItem item = new RadioMenuItem();
                item.setToggleGroup(group);
                item.setOnAction(evt -> getItem().setType(type));
                item.setGraphic(getTypeIcon(type));
                typeButton.getItems().add(item);
                typeItemMap.put(type, item);
            }

            itemProperty().addListener((observable, oldLayer, newLayer) -> {
                if (newLayer != null) {
                    Bindings.bindBidirectional(visibleButton.selectedProperty(), newLayer.visibleProperty());
                    Bindings.bindBidirectional(lockedButton.selectedProperty(), newLayer.lockedProperty());
                    Bindings.bindBidirectional(nameField.textProperty(), newLayer.nameProperty());

                    newLayer.typeProperty().addListener(weakTypeChangeListener);

                    // lock state might change at any time -> update lock button
                    lockedButton.setGraphic(getLockIcon(newLayer));
                    newLayer.lockedProperty().addListener(weakLockChangeListener);

                    typeButton.setGraphic(getTypeIcon(newLayer.getType()));

                    if (layerContentView != null) {
                        layerContentView.setLayer(newLayer);
                    }
                }
                if (oldLayer != null) {
                    Bindings.unbindBidirectional(visibleButton.selectedProperty(), oldLayer.visibleProperty());
                    Bindings.unbindBidirectional(lockedButton.selectedProperty(), oldLayer.lockedProperty());
                    Bindings.unbindBidirectional(nameField.textProperty(), oldLayer.nameProperty());
                }
            });

            vbox = new VBox();
            vbox.setFillWidth(true);
            vbox.getChildren().add(box);
            vbox.visibleProperty().bind(Bindings.isNotNull(itemProperty()));

            selectedProperty().addListener(it -> {
                if (isSelected()) {
                    layerContentView = new LayerContentView(getWorkbench());
                    layerContentView.setLayer(getItem());
                    vbox.getChildren().add(layerContentView);
                } else if (layerContentView != null) {
                    vbox.getChildren().remove(layerContentView);
                    layerContentView = null;
                }
            });

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(vbox);
        }

        private LayerContentView layerContentView;

        private InvalidationListener typeChangeListener = it -> {
            Layer layer = getItem();
            if (layer != null) {
                LayerType layerType = layer.getType();
                RadioMenuItem item = typeItemMap.get(layerType);
                item.setSelected(true);
                typeButton.setGraphic(getTypeIcon(layerType));
            }
        };

        private WeakInvalidationListener weakTypeChangeListener = new WeakInvalidationListener(typeChangeListener);

        private InvalidationListener lockChangeListener = it -> lockedButton.setGraphic(getLockIcon(getItem()));

        private WeakInvalidationListener weakLockChangeListener = new WeakInvalidationListener(lockChangeListener);

        private Node getTypeIcon(LayerType type) {
            Node view = null;

            switch (type) {
                case CODE:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.GEARS);
                    break;
                case TEXT:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.FONT);
                    break;
                case FXML:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.CODE);
                    break;
                case IMAGE:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.IMAGE);
                    break;
                case VIDEO:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.VIDEO_CAMERA);
                    break;
                case HTML:
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.HTML5);
                    break;
            }

            view.getStyleClass().add("layer-button");

            return view;
        }

        private Node getLockIcon(Layer layer) {
            Node view = null;

            if (layer != null) {
                if (layer.isLocked()) {
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.LOCK);
                } else {
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.UNLOCK_ALT);
                }

                view.getStyleClass().add("layer-button");
            }

            return view;
        }
    }
}

