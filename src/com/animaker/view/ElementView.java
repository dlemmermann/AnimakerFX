package com.animaker.view;

import com.animaker.model.Element;
import com.animaker.model.Element.LayoutStrategy;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 20.12.16.
 */
public abstract class ElementView<T extends Element> extends StackPane {

    private SlideView slideView;
    private final T element;

    public ElementView(SlideView slideView, T element) {
        this.slideView = slideView;
        this.element = element;

        getStyleClass().add("element");

        visibleProperty().bind(element.visibleProperty());

        setFocusTraversable(true);

        layoutXProperty().bindBidirectional(element.layoutXProperty());
        layoutYProperty().bindBidirectional(element.layoutYProperty());

        managedProperty().bind(element.layoutStrategyProperty().isEqualTo(LayoutStrategy.ABSOLUTE));

        styleProperty().bindBidirectional(element.styleProperty());

        prefWidthProperty().bind(element.widthProperty());
        prefHeightProperty().bind(element.heightProperty());
    }

    public final PresentationView getPresentationView() {
        return slideView.getPresentationView();
    }

    public final SlideView getSlideView() {
        return slideView;
    }

    public final T getElement() {
        return element;
    }

    public final void setupAnimation() {
        element.getOpeningTransitions().forEach(transition -> transition.setup(this));
    }

    public final void configureAnimation(Timeline timeline) {
        element.getOpeningTransitions().forEach(transition -> transition.configure(this, timeline));
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
}
