package com.animaker.view;

import com.animaker.model.Slide;
import com.animaker.view.skins.SlideViewSkin;
import javafx.animation.Timeline;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lemmi on 19.12.16.
 */
public class SlideView extends Control {

    private SliderView sliderView;
    private final Slide slide;
    private Timeline timeline = new Timeline();

    public SlideView(SliderView sliderView, Slide slide) {
        this.sliderView = Objects.requireNonNull(sliderView);
        this.slide = Objects.requireNonNull(slide);

        getStyleClass().add("slide");

        addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
//            if (evt.getCode().equals(KeyCode.SPACE)) {
            switch (timeline.getStatus()) {
                case RUNNING:
                    stop();
                    break;
                case STOPPED:
                    play();
                    break;
                case PAUSED:
                    resume();
                    break;
            }
            //          }
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SlideViewSkin(this);
    }

    public final SliderView getSliderView() {
        return sliderView;
    }

    public final Slide getSlide() {
        return slide;
    }

    public final void play() {

        if (timeline.getStatus().equals(Worker.State.RUNNING)) {
            timeline.stop();
            timeline.getKeyFrames().clear();
        }

        final List<LayerView> layerViews = getLayerViews();
        System.out.println(layerViews.size() + " layer views");
        layerViews.forEach(view -> view.setupAnimation());
        layerViews.forEach(view -> view.configureAnimation(timeline));

        timeline.playFromStart();
    }

    private List<LayerView> getLayerViews() {
        List<LayerView> views = new ArrayList<>();
        doGetLayerViews(this, views);
        return views;
    }

    private void doGetLayerViews(Parent parent, List<LayerView> views) {
        views.addAll(parent.getChildrenUnmodifiable().stream()
                .filter(child -> child instanceof LayerView)
                .map(child -> (LayerView) child)
                .collect(Collectors.toList()));

        parent.getChildrenUnmodifiable().forEach(child -> {
            if (child instanceof Parent) {
                doGetLayerViews((Parent) child, views);
            }
        });
    }

    public final void stop() {
        timeline.stop();
    }

    public final void resume() {
        timeline.play();
    }
}
