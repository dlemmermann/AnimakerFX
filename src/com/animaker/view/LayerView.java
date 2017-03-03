package com.animaker.view;

import com.animaker.model.Layer;
import com.animaker.view.skins.LayerViewSkin;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

    public final PresentationView getPresentationView() {
        return slideView.getPresentationView();
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

    // play support (for media)

    private final BooleanProperty play = new SimpleBooleanProperty(this, "play", false);

    public final BooleanProperty playProperty() {
        return play;
    }

    public final void setPlay(boolean play) {
        this.play.set(play);
    }

    public final boolean isPlay() {
        return play.get();
    }

    public final void setupAnimation() {
        layer.getOpeningTransitions().forEach(transition -> transition.setup(this));
    }

    public final void configureAnimation(Timeline timeline) {
        layer.getOpeningTransitions().forEach(transition -> transition.configure(this, timeline));
    }
}
