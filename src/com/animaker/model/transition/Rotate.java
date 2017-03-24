package com.animaker.model.transition;

import com.animaker.view.ElementView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 19.12.16.
 */
@XmlType(name = "rotate")
public class Rotate extends Transition {

    public Rotate() {
        super("Rotate");
    }

    // rotation

    private final DoubleProperty rotation = new SimpleDoubleProperty(this, "rotation", 360);

    public final DoubleProperty rotationProperty() {
        return rotation;
    }

    public final double getRotation() {
        return rotation.get();
    }

    public final void setRotation(double rotation) {
        this.rotation.set(rotation);
    }

    @Override
    public void setup(ElementView elementView) {
        elementView.setRotate(elementView.getElement().getRotation());
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(elementView.rotateProperty(), elementView.getElement().getRotation(), getInterpolator())),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(elementView.rotateProperty(), getRotation(), getInterpolator())));
    }
}
