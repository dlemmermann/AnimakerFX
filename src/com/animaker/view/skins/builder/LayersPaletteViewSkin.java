package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.model.Slide;
import com.animaker.view.builder.LayersPaletteView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LayersPaletteViewSkin extends SkinBase<LayersPaletteView> {

    private ListView<Layer> listView;

    public LayersPaletteViewSkin(LayersPaletteView view) {
        super(view);

        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(listView -> new LayerCell());
        listView.setFocusTraversable(false);
        view.selectedLayerProperty().bind(listView.getSelectionModel().selectedItemProperty());

        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Node plusIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS);
        plusIcon.getStyleClass().add("palette-button");

        Button addLayerButton = new Button();
        addLayerButton.setOnAction(evt -> {
            Slide slide = view.getSlide();
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

        view.slideProperty().addListener(it -> updateView());
        updateView();
    }

    private void updateView() {
        Slide slide = getSkinnable().getSlide();
        if (slide != null) {
            listView.setItems(slide.getLayers());
        } else {
            listView.setItems(null);
        }

        if (!listView.getItems().isEmpty()) {
            listView.getSelectionModel().select(0);
        }
    }

    static class LayerCell extends ListCell<Layer> {

        private Label previewLabel = new Label();
        private ToggleButton lockedButton = new ToggleButton();
        private ToggleButton visibleButton = new ToggleButton();
        private TextField nameField = new TextField();

        public LayerCell() {
            Node eyeIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EYE);
            eyeIcon.getStyleClass().add("layer-button");

            visibleButton.setGraphic(eyeIcon);

            HBox.setHgrow(nameField, Priority.ALWAYS);
            HBox.setHgrow(visibleButton, Priority.NEVER);
            HBox.setHgrow(lockedButton, Priority.NEVER);
            HBox.setHgrow(previewLabel, Priority.NEVER);

            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().setAll(previewLabel, nameField, visibleButton, lockedButton);
            box.visibleProperty().bind(Bindings.isNotNull(itemProperty()));

            itemProperty().addListener((observable, oldLayer, newLayer) -> {
                if (newLayer != null) {
                    Bindings.bindBidirectional(visibleButton.selectedProperty(), newLayer.visibleProperty());
                    Bindings.bindBidirectional(lockedButton.selectedProperty(), newLayer.lockedProperty());
                    Bindings.bindBidirectional(nameField.textProperty(), newLayer.nameProperty());

                    // layer type might change at any time -> update type icon
                    previewLabel.setGraphic(getTypeIcon(newLayer));
                    newLayer.typeProperty().addListener(weakTypeChangeListener);

                    // lock state might change at any time -> update lock button
                    lockedButton.setGraphic(getLockIcon(newLayer));
                    newLayer.lockedProperty().addListener(weakLockChangeListener);
                }
                if (oldLayer != null) {
                    Bindings.unbindBidirectional(visibleButton.selectedProperty(), oldLayer.visibleProperty());
                    Bindings.unbindBidirectional(lockedButton.selectedProperty(), oldLayer.lockedProperty());
                    Bindings.unbindBidirectional(nameField.textProperty(), oldLayer.nameProperty());
                }
            });

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(box);
        }

        private InvalidationListener typeChangeListener = it -> previewLabel.setGraphic(getTypeIcon(getItem()));

        private WeakInvalidationListener weakTypeChangeListener = new WeakInvalidationListener(typeChangeListener);

        private InvalidationListener lockChangeListener = it -> lockedButton.setGraphic(getLockIcon(getItem()));

        private WeakInvalidationListener weakLockChangeListener = new WeakInvalidationListener(lockChangeListener);

        private Node getTypeIcon(Layer layer) {
            Node view = null;

            if (layer != null) {
                switch (layer.getType()) {
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
                    default:
                        throw new IllegalArgumentException("unsupported layer type: " + layer.getType());
                }

                view.getStyleClass().add("layer-button");
            }


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
