package com.animaker.view.skins;

import com.animaker.model.Slide;
import com.animaker.view.LayerView;
import com.animaker.view.SlideView;
import javafx.beans.Observable;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class SlideViewSkin extends SkinBase<SlideView> {

    private final StackPane stackPane;
    private final Slide slide;
    private final ImageView backgroundImage;

    public SlideViewSkin(SlideView view) {
        super(view);

        slide = view.getSlide();
        slide.getLayers().addListener((Observable it) -> buildSlide());

        stackPane = new StackPane();
        stackPane.prefWidthProperty().bind(getSkinnable().widthProperty());
        stackPane.prefHeightProperty().bind(getSkinnable().heightProperty());
        stackPane.maxWidthProperty().bind(getSkinnable().widthProperty());
        stackPane.maxHeightProperty().bind(getSkinnable().heightProperty());
        stackPane.minWidthProperty().bind(getSkinnable().widthProperty());
        stackPane.minHeightProperty().bind(getSkinnable().heightProperty());

        getChildren().add(stackPane);

        backgroundImage = new ImageView();
        backgroundImage.imageProperty().bind(slide.backgroundImageProperty());

        buildSlide();
    }

    private void buildSlide() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(backgroundImage);
        slide.getLayers().forEach(layer -> {
            final LayerView layerView = new LayerView(getSkinnable(), layer);
            layerView.prefWidthProperty().bind(stackPane.widthProperty());
            layerView.prefHeightProperty().bind(stackPane.heightProperty());
            layerView.maxWidthProperty().bind(stackPane.widthProperty());
            layerView.maxHeightProperty().bind(stackPane.heightProperty());
            layerView.minWidthProperty().bind(stackPane.widthProperty());
            layerView.minHeightProperty().bind(stackPane.heightProperty());
            stackPane.getChildren().add(layerView);
        });
    }
}
