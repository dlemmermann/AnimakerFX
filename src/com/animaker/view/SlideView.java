package com.animaker.view;

import com.animaker.model.Layer;
import com.animaker.model.Layer.Side;
import com.animaker.model.Slide;
import com.animaker.view.PresentationView.Status;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lemmi on 19.12.16.
 */
public class SlideView extends Pane {

    private final PresentationView presentationView;
    private final Slide slide;
    private final Timeline timeline = new Timeline();
    private final ImageView backgroundImage;

    public SlideView(PresentationView presentationView, Slide slide) {
        this.presentationView = Objects.requireNonNull(presentationView);
        this.slide = Objects.requireNonNull(slide);

        getStyleClass().add("slide");

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

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        getChildren().forEach(child -> {
            if (child instanceof LayerView && !child.isManaged()) {
                layoutLayer((LayerView) child);
            }
        });
    }

    private void layoutLayer(LayerView layerView) {
        Layer layer = layerView.getLayer();
        switch (layer.getLayoutStrategy()) {
            case SIDES:
                layoutLayerSides(layerView);
                break;
            case POSITION:
                layoutLayerPosition(layerView);
                break;
        }
    }

    private void layoutLayerSides(LayerView layerView) {
        Side side = layerView.getLayer().getSide();
        final double prefHeight = layerView.prefHeight(getWidth());
        final double prefWidth = layerView.prefWidth(getHeight());
        switch (side) {
            case TOP:
                layerView.resizeRelocate(0, 0, getWidth(), prefHeight);
                break;
            case BOTTOM:
                layerView.resizeRelocate(0, getHeight() - prefHeight, getWidth(), prefHeight);
                break;
            case LEFT:
                layerView.resizeRelocate(0, 0, prefWidth, getHeight());
                break;
            case RIGHT:
                layerView.resizeRelocate(getWidth() - prefWidth, 0, prefWidth, getHeight());
                break;
        }
    }

    private void layoutLayerPosition(LayerView layerView) {
        Pos position = layerView.getLayer().getPosition();
        Insets insets = layerView.getInsets();

        final double prefHeight = layerView.prefHeight(getWidth());
        final double prefWidth = layerView.prefWidth(getHeight());
        switch (position) {
            case TOP_LEFT:
                layerView.resizeRelocate(insets.getLeft(), insets.getTop(), prefWidth, prefHeight);
                break;
            case TOP_CENTER:
                layerView.resizeRelocate(getWidth() / 2 - prefWidth / 2, insets.getTop(), prefWidth, prefHeight);
                break;
            case TOP_RIGHT:
                layerView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), insets.getTop(), prefWidth, prefHeight);
                break;
            case CENTER_LEFT:
                layerView.resizeRelocate(insets.getLeft(), getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case CENTER:
                layerView.resizeRelocate(getWidth() / 2 - prefWidth / 2, getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case CENTER_RIGHT:
                layerView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case BOTTOM_LEFT:
                layerView.resizeRelocate(insets.getLeft(), getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
            case BOTTOM_CENTER:
                layerView.resizeRelocate(getWidth() / 2 - prefWidth / 2, getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
            case BOTTOM_RIGHT:
                layerView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
        }
    }

    private final InvalidationListener layoutListener = it -> requestLayout();

    private final WeakInvalidationListener weakLayoutListener = new WeakInvalidationListener(layoutListener);

    private void buildSlide() {
        getChildren().clear();
        getChildren().add(backgroundImage);
        slide.getLayers().forEach(layer -> {
            final LayerView layerView = new LayerView(this, layer);
            getChildren().add(layerView);

            layer.layoutStrategyProperty().removeListener(weakLayoutListener);
            layer.sideProperty().removeListener(weakLayoutListener);
            layer.positionProperty().removeListener(weakLayoutListener);
            layer.styleProperty().removeListener(weakLayoutListener);

            layer.layoutStrategyProperty().addListener(weakLayoutListener);
            layer.sideProperty().addListener(weakLayoutListener);
            layer.positionProperty().addListener(weakLayoutListener);
            layer.styleProperty().addListener(weakLayoutListener);
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
