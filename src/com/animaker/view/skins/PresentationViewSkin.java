package com.animaker.view.skins;

import com.animaker.model.Slide;
import com.animaker.model.Presentation;
import com.animaker.view.SlideView;
import com.animaker.view.PresentationView;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Created by lemmi on 19.12.16.
 */
public class PresentationViewSkin extends SkinBase<PresentationView> {

    private final StackPane stackPane;
    private final Presentation presentation;
    private final ImageView backgroundImage;

    public PresentationViewSkin(PresentationView view) {
        super(view);

        view.setStyle("-fx-background-color: green;");
        stackPane = new StackPane();
        stackPane.setManaged(false);
        getChildren().add(stackPane);

        presentation = view.getPresentation();
        presentation.layoutProperty().addListener(it -> updateSize());
        presentation.getStylesheets().addListener((Observable it) -> updateStylesheets());

        backgroundImage = new ImageView();
        backgroundImage.imageProperty().bind(presentation.backgroundImageProperty());
        backgroundImage.setPreserveRatio(false);
        backgroundImage.fitWidthProperty().bind(stackPane.widthProperty());
        backgroundImage.fitHeightProperty().bind(stackPane.heightProperty());

        view.currentSlideProperty().addListener(it -> updateSlide());

        updateSlide();
        updateSize();
        updateStylesheets();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(view.widthProperty());
        clip.heightProperty().bind(view.heightProperty());
        view.setClip(clip);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        stackPane.resizeRelocate(contentX, contentY, contentWidth, contentHeight);
    }

    private void updateStylesheets() {
        getSkinnable().getStylesheets().setAll(presentation.getStylesheets());
    }

    private void updateSlide() {
        Slide slide = getSkinnable().getCurrentSlide();

        if (slide != null) {
            SlideView slideView = new SlideView(getSkinnable(), slide);
            StackPane.setAlignment(slideView, Pos.CENTER);
            stackPane.getChildren().setAll(backgroundImage, slideView);
        }
    }

    private void updateSize() {
        final PresentationView view = getSkinnable();

        // first unbind everything
        view.prefWidthProperty().unbind();
        view.minWidthProperty().unbind();
        view.maxWidthProperty().unbind();
        view.prefHeightProperty().unbind();
        view.minHeightProperty().unbind();
        view.maxHeightProperty().unbind();

        switch (presentation.getLayout()) {
            case FILL:
                view.setMinSize(0, 0);
                view.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                break;
            case FIXED_HEIGHT:
                view.prefHeightProperty().bind(presentation.heightProperty());
                view.minHeightProperty().bind(presentation.heightProperty());
                view.maxHeightProperty().bind(presentation.heightProperty());
                view.setMinWidth(0);
                view.setMaxWidth(Double.MAX_VALUE);
                break;
            case FIXED_WIDTH:
                view.prefWidthProperty().bind(presentation.widthProperty());
                view.minWidthProperty().bind(presentation.widthProperty());
                view.maxWidthProperty().bind(presentation.widthProperty());
                view.setMinHeight(0);
                view.setMaxHeight(Double.MAX_VALUE);
                break;
            case FIXED_SIZE:
                view.prefWidthProperty().bind(presentation.widthProperty());
                view.minWidthProperty().bind(presentation.widthProperty());
                view.maxWidthProperty().bind(presentation.widthProperty());
                view.prefHeightProperty().bind(presentation.heightProperty());
                view.minHeightProperty().bind(presentation.heightProperty());
                view.maxHeightProperty().bind(presentation.heightProperty());
                break;
        }
    }
}
