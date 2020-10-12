package com.animaker.model.transition;

import javax.xml.bind.annotation.XmlType;

import com.animaker.view.ElementView;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

@XmlType(name = "move-out")
public class MoveOut extends Move {

    public MoveOut() {
        super("Move Out");
    }

    // direction support

    private final ObjectProperty<TransitionDirection> direction = new SimpleObjectProperty<>(this, "direction", TransitionDirection.LEFT_TO_RIGHT);

    public final ObjectProperty<TransitionDirection> directionProperty() {
        return direction;
    }

    public final void setDirection(TransitionDirection direction) {
        this.direction.set(direction);
    }

    public final TransitionDirection getDirection() {
        return direction.get();
    }

    // bounds support

    private final BooleanProperty bounce = new SimpleBooleanProperty(this, "bounce", true);

    public final BooleanProperty bounceProperty() {
        return bounce;
    }

    public final boolean isBounce() {
        return bounce.get();
    }

    public final void setBounce(boolean bounce) {
        this.bounce.set(bounce);
    }

    @Override
    public void setup(ElementView elementView) {
        elementView.setTranslateX(getOffScreenX(elementView));
        elementView.setTranslateY(getOffScreenY(elementView));
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        KeyValue xValue1 = new KeyValue(elementView.translateXProperty(), 0, getInterpolator());
        KeyValue yValue1 = new KeyValue(elementView.translateYProperty(), 0, getInterpolator());
        KeyValue xValue2 = new KeyValue(elementView.translateXProperty(), getOffScreenX(elementView), getInterpolator());
        KeyValue yValue2 = new KeyValue(elementView.translateYProperty(), getOffScreenY(elementView), getInterpolator());
        KeyFrame frame1 = new KeyFrame(getDelay(), xValue1, yValue1);
        KeyFrame frame2 = new KeyFrame(getDuration().add(getDelay()), xValue2, yValue2);
        timeline.getKeyFrames().addAll(frame1, frame2);
    }

    private double getOffScreenX(ElementView view) {
        switch (getDirection()) {
            case LEFT_TO_RIGHT:
            case TOP_LEFT_TO_BOTTOM_RIGHT:
            case BOTTOM_LEFT_TO_TOP_RIGHT:
                return view.getPresentationView().getWidth();
            case RIGHT_TO_LEFT:
            case TOP_RIGHT_TO_BOTTOM_LEFT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
                return -view.getWidth();
            case BOTTOM_TO_TOP:
            case TOP_TO_BOTTOM:
            default:
                return 0;
        }
    }

    private double getOffScreenY(ElementView view) {
        switch (getDirection()) {
            case TOP_LEFT_TO_BOTTOM_RIGHT:
            case TOP_RIGHT_TO_BOTTOM_LEFT:
            case TOP_TO_BOTTOM:
                return view.getPresentationView().getHeight();
            case BOTTOM_LEFT_TO_TOP_RIGHT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
            case BOTTOM_TO_TOP:
                return -view.getHeight();
            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
            default:
                return 0;
        }
    }
}
