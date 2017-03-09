package com.animaker.view;

import com.animaker.model.Slide;
import com.animaker.view.PresentationView.Status;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lemmi on 19.12.16.
 */
public class SlideView extends StackPane {

    private final PresentationView presentationView;
    private final Slide slide;
    private final Timeline timeline = new Timeline();
    private final ImageView backgroundImage;

    public SlideView(PresentationView presentationView, Slide slide) {
        this.presentationView = Objects.requireNonNull(presentationView);
        this.slide = Objects.requireNonNull(slide);

        getStyleClass().add("slide");

        setManaged(false);

        presentationView.statusProperty().addListener(it -> {
            switch (presentationView.getStatus()) {
                case STOPPED:
                    stop();
                    break;
                case PLAY:
                    if (timeline.getStatus().equals(Animation.Status.PAUSED)) {
                        resume();
                    } else {
                        play();
                    }
                    break;
                case PAUSED:
                    pause();
                    break;
            }
        });

        timeline.setOnFinished(evt -> presentationView.setStatus(Status.STOPPED));

        slide.getLayers().addListener((Observable it) -> buildSlide());

        backgroundImage = new ImageView();
        backgroundImage.imageProperty().bind(slide.backgroundImageProperty());

        buildSlide();
    }

    private void buildSlide() {
        getChildren().clear();
        getChildren().add(backgroundImage);
        slide.getLayers().forEach(layer -> {
            final LayerView layerView = new LayerView(this, layer);
            getChildren().add(layerView);
        });
    }

    public final PresentationView getPresentationView() {
        return presentationView;
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
        System.out.println("SlideView: stopping timeline");
        timeline.stop();
    }

    public final void pause() {
        timeline.pause();
    }

    public final void resume() {
        timeline.play();
    }
}
