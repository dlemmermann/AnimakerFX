package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.model.Element.ElementType;
import com.animaker.model.Slide;
import com.animaker.model.elements.CodeElement;
import com.animaker.model.elements.FXMLElement;
import com.animaker.model.elements.HTMLElement;
import com.animaker.model.elements.ImageElement;
import com.animaker.model.elements.RegionElement;
import com.animaker.model.elements.TextElement;
import com.animaker.model.elements.VideoElement;
import com.animaker.view.builder.Workbench;
import com.animaker.view.builder.slide.SlideControlBase;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by lemmi on 21.12.16.
 */
public class ElementsPaletteView extends SlideControlBase {

    private ListView<Element> listView;

    public ElementsPaletteView(Workbench workbench) {
        super(workbench);

        getStyleClass().add("palette");

        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(listView -> new LayerCell());
        listView.setFocusTraversable(false);
        listView.getSelectionModel().selectedItemProperty()
                .addListener(it -> setSelectedLayer(listView.getSelectionModel().getSelectedItem()));

        workbench.selectedElementProperty().addListener(it -> {
            Element element = workbench.getSelectedElement();
            if (element != null) {
                listView.getSelectionModel().select(element);
                listView.scrollTo(element);
            }
        });


        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getStyleClass().add("button-bar");

        Node plusIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS);
        plusIcon.getStyleClass().add("palette-button");

        MenuButton addLayerButton = new MenuButton();
        for (ElementType type : ElementType.values()) {
            MenuItem item = new MenuItem(type.getDisplayName());
            item.setOnAction(evt -> createNewElement(type));
            addLayerButton.getItems().add(item);
        }

        addLayerButton.setGraphic(plusIcon);
        addLayerButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        hbox.getChildren().add(addLayerButton);

        Label title = new Label("Elements");
        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add("palette-title");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(listView);
        borderPane.setBottom(hbox);

        getChildren().add(borderPane);
    }

    private Element createNewElement(ElementType type) {
        switch (type) {
            case CODE:
                return new CodeElement();
            case FXML:
                return new FXMLElement();
            case HTML:
                return new HTMLElement();
            case IMAGE:
                return new ImageElement();
            case TEXT:
                return new TextElement();
            case VIDEO:
                return new VideoElement();
            case REGION:
                return new RegionElement();
            default:
                throw new IllegalArgumentException("unsupported element type " + type);
        }
    }

    private final ObjectProperty<Element> selectedLayer = new SimpleObjectProperty<>(this, "selectedElement");

    public final ObjectProperty<Element> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Element selectedElement) {
        this.selectedLayer.set(selectedElement);
    }

    public final Element getSelectedLayer() {
        return selectedLayer.get();
    }

    @Override
    protected void updateSlide(Slide oldSlide, Slide newSlide) {
        if (newSlide != null) {
            listView.setItems(newSlide.getElements());
        } else {
            listView.setItems(FXCollections.emptyObservableList());
        }

        if (!listView.getItems().isEmpty()) {
            listView.getSelectionModel().select(0);
        }
    }

    private Node getTypeIcon(ElementType type) {
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
            case REGION:
                view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TIMES_RECTANGLE);
                break;
        }

        view.getStyleClass().add("element-button");

        return view;
    }

    class LayerCell extends ListCell<Element> {

        private VBox vbox;
        private Label typeLabel = new Label();
        private ToggleButton lockedButton = new ToggleButton();
        private ToggleButton visibleButton = new ToggleButton();
        private TextField nameField = new TextField();

        public LayerCell() {
            Node eyeIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EYE);
            eyeIcon.getStyleClass().add("element-button");

            visibleButton.setGraphic(eyeIcon);

            HBox.setHgrow(nameField, Priority.ALWAYS);
            HBox.setHgrow(visibleButton, Priority.NEVER);
            HBox.setHgrow(lockedButton, Priority.NEVER);
            HBox.setHgrow(typeLabel, Priority.NEVER);

            HBox box = new HBox(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().setAll(typeLabel, nameField, visibleButton, lockedButton);

            itemProperty().addListener((observable, oldElement, newElement) -> {
                if (oldElement != null) {
                    Bindings.unbindBidirectional(visibleButton.selectedProperty(), oldElement.visibleProperty());
                    Bindings.unbindBidirectional(lockedButton.selectedProperty(), oldElement.lockedProperty());
                    Bindings.unbindBidirectional(nameField.textProperty(), oldElement.nameProperty());
                }

                if (newElement != null) {
                    Bindings.bindBidirectional(visibleButton.selectedProperty(), newElement.visibleProperty());
                    Bindings.bindBidirectional(lockedButton.selectedProperty(), newElement.lockedProperty());
                    Bindings.bindBidirectional(nameField.textProperty(), newElement.nameProperty());

                    // lock state might change at any time -> update lock button
                    lockedButton.setGraphic(getLockIcon(newElement));
                    newElement.lockedProperty().addListener(weakLockChangeListener);

                    typeLabel.setGraphic(getTypeIcon(newElement.getType()));
                }
            });

            vbox = new VBox();
            vbox.setFillWidth(true);
            vbox.getChildren().add(box);
            vbox.visibleProperty().bind(Bindings.isNotNull(itemProperty()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(vbox);
        }

        private InvalidationListener lockChangeListener = it -> lockedButton.setGraphic(getLockIcon(getItem()));

        private WeakInvalidationListener weakLockChangeListener = new WeakInvalidationListener(lockChangeListener);


        private Node getLockIcon(Element element) {
            Node view = null;

            if (element != null) {
                if (element.isLocked()) {
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.LOCK);
                } else {
                    view = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.UNLOCK_ALT);
                }

                view.getStyleClass().add("element-button");
            }

            return view;
        }
    }
}

