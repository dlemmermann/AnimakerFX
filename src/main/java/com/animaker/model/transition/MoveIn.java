package com.animaker.model.transition;

import javax.xml.bind.annotation.XmlType;

import com.animaker.view.ElementView;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

@XmlType(name = "move-in")
public class MoveIn extends Move {

    public MoveIn() {
        super("Move In");
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

    // bounce support

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

    // bounce offset

    private final DoubleProperty bounceOffset = new SimpleDoubleProperty(this, "bounceOffset", 40) {
        @Override
        public void set(double newValue) {
            if (newValue < 0) {
                throw new IllegalArgumentException("bounce offset can not be negative");
            }
            super.set(newValue);
        }
    };

    public final DoubleProperty bounceOffsetProperty() {
        return bounceOffset;
    }

    public final double getBounceOffset() {
        return bounceOffset.get();
    }

    public final void setBounceOffset(double bounceOffset) {
        this.bounceOffset.set(bounceOffset);
    }

    @Override
    public void setup(ElementView elementView) {
        elementView.setTranslateX(getOffScreenX(elementView));
        elementView.setTranslateY(getOffScreenY(elementView));
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        System.out.println("move in duration: " + getDuration());
        KeyValue xValue1 = new KeyValue(elementView.translateXProperty(), getOffScreenX(elementView), getInterpolator());
        KeyValue yValue1 = new KeyValue(elementView.translateYProperty(), getOffScreenY(elementView), getInterpolator());

        // bounds location
        KeyValue xValue2 = new KeyValue(elementView.translateXProperty(), getBoundsOffsetX(), getInterpolator());
        KeyValue yValue2 = new KeyValue(elementView.translateYProperty(), getBoundsOffsetY(), getInterpolator());

        KeyValue xValue3 = new KeyValue(elementView.translateXProperty(), 0, getInterpolator());
        KeyValue yValue3 = new KeyValue(elementView.translateYProperty(), 0, getInterpolator());

        KeyFrame frame1 = new KeyFrame(getDelay(), xValue1, yValue1);
        KeyFrame frame2 = new KeyFrame(getDuration().add(getDelay()).subtract(getDuration().divide(4)), xValue2, yValue2);
        KeyFrame frame3 = new KeyFrame(getDuration().add(getDelay()), xValue3, yValue3);

        if (isBounce()) {
            timeline.getKeyFrames().addAll(frame1, frame2, frame3);
        } else {
            timeline.getKeyFrames().addAll(frame1, frame3);
        }
    }

    private double getBoundsOffsetX() {
        switch (getDirection()) {
            case LEFT_TO_RIGHT:
            case TOP_LEFT_TO_BOTTOM_RIGHT:
            case BOTTOM_LEFT_TO_TOP_RIGHT:
                return +getBounceOffset();
            case RIGHT_TO_LEFT:
            case TOP_RIGHT_TO_BOTTOM_LEFT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
                return -getBounceOffset();
            case BOTTOM_TO_TOP:
            case TOP_TO_BOTTOM:
            default:
                return 0;
        }
    }

    private double getBoundsOffsetY() {
        switch (getDirection()) {
            case TOP_LEFT_TO_BOTTOM_RIGHT:
            case TOP_RIGHT_TO_BOTTOM_LEFT:
            case TOP_TO_BOTTOM:
                return +getBounceOffset();
            case BOTTOM_LEFT_TO_TOP_RIGHT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
            case BOTTOM_TO_TOP:
                return -getBounceOffset();
            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
            default:
                return 0;
        }
    }

    private double getOffScreenX(ElementView view) {
        switch (getDirection()) {
            case LEFT_TO_RIGHT:
            case TOP_LEFT_TO_BOTTOM_RIGHT:
            case BOTTOM_LEFT_TO_TOP_RIGHT:
                return -view.getLayoutX() - view.getWidth();
            case RIGHT_TO_LEFT:
            case TOP_RIGHT_TO_BOTTOM_LEFT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
                return view.getPresentationView().getWidth();
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
                return -view.getLayoutY() - view.getHeight();
            case BOTTOM_LEFT_TO_TOP_RIGHT:
            case BOTTOM_RIGHT_TO_TOP_LEFT:
            case BOTTOM_TO_TOP:
                return view.getPresentationView().getHeight();
            case LEFT_TO_RIGHT:
            case RIGHT_TO_LEFT:
            default:
                return 0;
        }
    }
}
