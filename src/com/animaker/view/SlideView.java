package com.animaker.view;

import com.animaker.model.Element;
import com.animaker.model.Element.Side;
import com.animaker.model.Project;
import com.animaker.model.Slide;
import com.animaker.model.elements.CodeElement;
import com.animaker.model.elements.FXMLElement;
import com.animaker.model.elements.HTMLElement;
import com.animaker.model.elements.ImageElement;
import com.animaker.model.elements.RegionElement;
import com.animaker.model.elements.TextElement;
import com.animaker.model.elements.VideoElement;
import com.animaker.view.PresentationView.Status;
import com.animaker.view.elements.CodeElementView;
import com.animaker.view.elements.FXMLElementView;
import com.animaker.view.elements.HTMLElementView;
import com.animaker.view.elements.ImageElementView;
import com.animaker.view.elements.RegionElementView;
import com.animaker.view.elements.TextElementView;
import com.animaker.view.elements.VideoElementView;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
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

        slide.getElements().addListener((Observable it) -> buildSlide());

        backgroundImage = new ImageView();
        slide.backgroundImageFileNameProperty().addListener(it -> updateBackgroundImage());
        buildSlide();
        updateBackgroundImage();

        timeline.totalDurationProperty().addListener(it -> System.out.println("total duration: " + timeline.getTotalDuration()));
    }

    public Timeline getTimeline() {
        return timeline;
    }

    private void updateBackgroundImage() {
        String fileName = slide.getBackgroundImageFileName();
        if (fileName != null) {
            Project project = getPresentationView().getProject();
            File file = project.getFile(fileName);
            try (FileInputStream in = new FileInputStream(file)) {
                final Image image = new Image(in);
                final BackgroundRepeat repeatX = slide.getRepeatX();
                final BackgroundRepeat repeatY = slide.getRepeatY();
                BackgroundSize size = BackgroundSize.DEFAULT;

                // TODO: how does "size" work?
                if (repeatX.equals(BackgroundRepeat.NO_REPEAT)) {
                    size = new BackgroundSize(100, 100, true, true, false, true);
                }
                final BackgroundImage backgroundImage = new BackgroundImage(image, repeatX, repeatY, BackgroundPosition.CENTER, size);
                setBackground(new Background(backgroundImage));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        getChildren().forEach(child -> {
            if (child instanceof ElementView && !child.isManaged()) {
                layoutLayer((ElementView) child);
            }
        });
    }

    private void layoutLayer(ElementView elementView) {
        Element element = elementView.getElement();
        switch (element.getLayoutStrategy()) {
            case SIDES:
                layoutLayerSides(elementView);
                break;
            case POSITION:
                layoutLayerPosition(elementView);
                break;
        }
    }

    private void layoutLayerSides(ElementView elementView) {
        Side side = elementView.getElement().getSide();
        final double prefHeight = elementView.prefHeight(getWidth());
        final double prefWidth = elementView.prefWidth(getHeight());
        switch (side) {
            case TOP:
                elementView.resizeRelocate(0, 0, getWidth(), prefHeight);
                break;
            case BOTTOM:
                elementView.resizeRelocate(0, getHeight() - prefHeight, getWidth(), prefHeight);
                break;
            case LEFT:
                elementView.resizeRelocate(0, 0, prefWidth, getHeight());
                break;
            case RIGHT:
                elementView.resizeRelocate(getWidth() - prefWidth, 0, prefWidth, getHeight());
                break;
        }
    }

    private void layoutLayerPosition(ElementView elementView) {
        Pos position = elementView.getElement().getPosition();
        Insets insets = elementView.getInsets();

        final double prefHeight = elementView.prefHeight(getWidth());
        final double prefWidth = elementView.prefWidth(getHeight());
        switch (position) {
            case TOP_LEFT:
                elementView.resizeRelocate(insets.getLeft(), insets.getTop(), prefWidth, prefHeight);
                break;
            case TOP_CENTER:
                elementView.resizeRelocate(getWidth() / 2 - prefWidth / 2, insets.getTop(), prefWidth, prefHeight);
                break;
            case TOP_RIGHT:
                elementView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), insets.getTop(), prefWidth, prefHeight);
                break;
            case CENTER_LEFT:
                elementView.resizeRelocate(insets.getLeft(), getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case CENTER:
                elementView.resizeRelocate(getWidth() / 2 - prefWidth / 2, getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case CENTER_RIGHT:
                elementView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), getHeight() / 2 - prefHeight / 2, prefWidth, prefHeight);
                break;
            case BOTTOM_LEFT:
                elementView.resizeRelocate(insets.getLeft(), getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
            case BOTTOM_CENTER:
                elementView.resizeRelocate(getWidth() / 2 - prefWidth / 2, getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
            case BOTTOM_RIGHT:
                elementView.resizeRelocate(getWidth() - prefWidth - insets.getRight(), getHeight() - prefHeight - insets.getBottom(), prefWidth, prefHeight);
                break;
        }
    }

    private final InvalidationListener layoutListener = it -> requestLayout();

    private final WeakInvalidationListener weakLayoutListener = new WeakInvalidationListener(layoutListener);

    private void buildSlide() {
        getChildren().clear();
        getChildren().add(backgroundImage);
        slide.getElements().forEach(element -> {
            final ElementView elementView = createElementView(element);
            getChildren().add(elementView);

            element.layoutStrategyProperty().removeListener(weakLayoutListener);
            element.sideProperty().removeListener(weakLayoutListener);
            element.positionProperty().removeListener(weakLayoutListener);
            element.styleProperty().removeListener(weakLayoutListener);

            element.layoutStrategyProperty().addListener(weakLayoutListener);
            element.sideProperty().addListener(weakLayoutListener);
            element.positionProperty().addListener(weakLayoutListener);
            element.styleProperty().addListener(weakLayoutListener);
        });
    }

    private ElementView<?> createElementView(Element element) {
        switch (element.getType()) {
            case CODE:
                return new CodeElementView(this, (CodeElement) element);
            case FXML:
                return new FXMLElementView(this, (FXMLElement) element);
            case HTML:
                return new HTMLElementView(this, (HTMLElement) element);
            case REGION:
                return new RegionElementView(this, (RegionElement) element);
            case IMAGE:
                return new ImageElementView(this, (ImageElement) element);
            case TEXT:
                return new TextElementView(this, (TextElement) element);
            case VIDEO:
                return new VideoElementView(this, (VideoElement) element);
        }

        throw new UnsupportedOperationException("element of type " + element.getClass().getName() + " not supported, yet.");
    }

    public final PresentationView getPresentationView() {
        return presentationView;
    }

    public final Slide getSlide() {
        return slide;
    }

    public final void reset() {
        final List<ElementView> elementViews = getElementViews();
        elementViews.forEach(view -> view.reset());
    }

    public final void play() {
        if (timeline.getStatus().equals(Worker.State.RUNNING)) {
            timeline.stop();
        }

        timeline.getKeyFrames().clear();

        final List<ElementView> elementViews = getElementViews();
        elementViews.forEach(view -> view.setupAnimation());
        elementViews.forEach(view -> view.configureAnimation(timeline));
        System.out.println("key frame count: " + timeline.getKeyFrames().size());

        timeline.playFromStart();
    }

    public final void jumpTo(Duration duration) {
        if (timeline.getStatus().equals(Worker.State.RUNNING)) {
            timeline.stop();
        }

        timeline.getKeyFrames().clear();

        final List<ElementView> elementViews = getElementViews();
        elementViews.forEach(view -> view.setupAnimation());
        elementViews.forEach(view -> view.configureAnimation(timeline));

        timeline.play();
        timeline.jumpTo(duration);
        timeline.pause();
    }

    private List<ElementView> getElementViews() {
        List<ElementView> views = new ArrayList<>();
        doGetElementViews(this, views);
        return views;
    }

    private void doGetElementViews(Parent parent, List<ElementView> views) {
        views.addAll(parent.getChildrenUnmodifiable().stream()
                .filter(child -> child instanceof ElementView)
                .map(child -> (ElementView) child)
                .collect(Collectors.toList()));

        parent.getChildrenUnmodifiable().forEach(child -> {
            if (child instanceof Parent) {
                doGetElementViews((Parent) child, views);
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
