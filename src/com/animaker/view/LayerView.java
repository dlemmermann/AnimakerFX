package com.animaker.view;

import com.animaker.model.Layer;
import com.animaker.view.skins.LayerViewSkin;
import javafx.animation.Timeline;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerView extends Control {

    private SlideView slideView;
    private final Layer layer;

    public LayerView(SlideView slideView, Layer layer) {
        this.slideView = slideView;
        this.layer = layer;

        getStyleClass().add("layer");

        visibleProperty().bind(layer.visibleProperty());

        setFocusTraversable(true);
    }

    public SliderView getSliderView() {
        return slideView.getSliderView();
    }

    public final SlideView getSlideView() {
        return slideView;
    }

    public final Layer getLayer() {
        return layer;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayerViewSkin(this);
    }


    public void setupAnimation() {
        layer.getOpeningTransitions().forEach(transition -> transition.setup(this));
    }

    public void configureAnimation(Timeline timeline) {
        layer.getOpeningTransitions().forEach(transition -> transition.configure(this, timeline));
    }
}
