package com.animaker.view.skins.builder;

import com.animaker.model.*;
import com.animaker.view.builder.SlidesPaletteView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by lemmi on 21.12.16.
 */
public class SlidesPaletteViewSkin extends SkinBase<SlidesPaletteView> {

    private ListView<Slide> listView;

    public SlidesPaletteViewSkin(SlidesPaletteView view) {
        super(view);

        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setFocusTraversable(false);
        listView.setCellFactory(listView -> new SlideCell());
        view.selectedSlideProperty().bind(listView.getSelectionModel().selectedItemProperty());

        HBox hbox = new HBox();
        hbox.setFillHeight(true);
        hbox.setAlignment(Pos.CENTER_LEFT);

        Node plusIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS);
        plusIcon.getStyleClass().add("palette-button");

        Button addSlideButton = new Button();
        addSlideButton.setOnAction(evt -> {
            Presentation presentation = view.getPresentation();
            if (presentation != null) {
                Slide slide = new Slide("Untitled");
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

        view.presentationProperty().addListener(it -> updateView());
        updateView();
    }

    private void updateView() {
        Presentation presentation = getSkinnable().getPresentation();
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
