package com.animaker.view.builder.slide;

import com.animaker.model.Presentation;
import com.animaker.model.Slide;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class SlidesPaletteView extends StackPane {

    private Workbench workbench;
    private ListView<Slide> listView;

    public SlidesPaletteView(Workbench workbench) {
        this.workbench = Objects.requireNonNull(workbench);

        getStyleClass().add("palette");

        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setFocusTraversable(false);
        listView.setCellFactory(listView -> new SlideCell());
        listView.getSelectionModel().selectedItemProperty().addListener(it -> setSelectedSlide(listView.getSelectionModel().getSelectedItem()));

        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getStyleClass().add("button-bar");

        Node plusIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS);
        plusIcon.getStyleClass().add("palette-button");

        Button addSlideButton = new Button();
        addSlideButton.setOnAction(evt -> {
            Presentation presentation = getPresentation();
            if (presentation != null) {
                Slide slide = new Slide();
                slide.setName("Untitled");
                presentation.getSlides().add(slide);
            }
        });

        addSlideButton.setGraphic(plusIcon);
        addSlideButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        hbox.getChildren().add(addSlideButton);

        Label title = new Label("Slides");
        title.setMaxWidth(Double.MAX_VALUE);
        title.getStyleClass().add("palette-title");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(title);
        borderPane.setCenter(listView);
        borderPane.setBottom(hbox);

        getChildren().add(borderPane);

        presentationProperty().addListener(it -> updateView());
    }

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return getWorkbench().getProject();
    }

    // presentation support

    private final ObjectProperty<Presentation> presentation = new SimpleObjectProperty<>(this, "presentation");

    public final ObjectProperty<Presentation> presentationProperty() {
        return presentation;
    }

    public final Presentation getPresentation() {
        return presentation.get();
    }

    public final void setPresentation(Presentation presentation) {
        this.presentation.set(presentation);
    }

    // selected slide support

    private final ObjectProperty<Slide> selectedSlide = new SimpleObjectProperty<>(this, "selectedSlide");

    public final ObjectProperty<Slide> selectedSlideProperty() {
        return selectedSlide;
    }

    public final Slide getSelectedSlide() {
        return selectedSlide.get();
    }

    public final void setSelectedSlide(Slide selectedSlide) {
        this.selectedSlide.set(selectedSlide);
    }

    private void updateView() {
        Presentation presentation = getPresentation();
        if (presentation != null) {
            listView.setItems(presentation.getSlides());
        } else {
            listView.setItems(FXCollections.emptyObservableList());
        }

        if (!listView.getItems().isEmpty()) {
            listView.getSelectionModel().select(0);
        }
    }

    static class SlideCell extends ListCell<Slide> {

        public SlideCell() {
        }

        @Override
        protected void updateItem(Slide item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                textProperty().bind(item.nameProperty());
            }
        }
    }
}
