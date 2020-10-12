package com.animaker.model.transition;

import javax.xml.bind.annotation.XmlType;

import com.animaker.view.ElementView;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by lemmi on 19.12.16.
 */
@XmlType(name = "scale")
public class Scale extends Transition {

    public enum ScaleDirection {
        UP,
        DOWN
    }

    public Scale() {
        super("Scale");
    }

    // scale direction property

    private final ObjectProperty<ScaleDirection> direction = new SimpleObjectProperty<>(this, "direction", ScaleDirection.UP);

    public final ObjectProperty<ScaleDirection> directionProperty() {
        return direction;
    }

    public final ScaleDirection getDirection() {
        return direction.get();
    }

    public final void setDirection(ScaleDirection direction) {
        this.direction.set(direction);
    }

    // scale X property

    private final DoubleProperty scaleX = new SimpleDoubleProperty(this, "scaleX", 1.2);

    public final DoubleProperty scaleXProperty() {
        return scaleX;
    }

    public final void setScaleX(double scale) {
        this.scaleX.set(scale);
    }

    public double getScaleX() {
        return scaleX.get();
    }

    // scale Y property

    private final DoubleProperty scaleY = new SimpleDoubleProperty(this, "scaleY", 1.2);

    public final DoubleProperty scaleYProperty() {
        return scaleY;
    }

    public final void setScaleY(double scale) {
        this.scaleY.set(scale);
    }

    public double getScaleY() {
        return scaleY.get();
    }

    // rotation

    @Override
    public void setup(ElementView elementView) {
        elementView.setScaleX(elementView.getElement().getScaleX());
        elementView.setScaleY(elementView.getElement().getScaleY());
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        timeline.getKeyFrames().add(
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(elementView.scaleXProperty(),  getScaleX(), getInterpolator()),
                        new KeyValue(elementView.scaleYProperty(),  getScaleY(), getInterpolator())));
    }
}
