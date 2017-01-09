package com.animaker.model.transition;

import com.animaker.view.LayerView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Created by lemmi on 19.12.16.
 */
public class Rotate extends Transition {

    public Rotate() {
        super("Rotate");
    }

    // rotation

    private final DoubleProperty rotation = new SimpleDoubleProperty(this, "rotation", 90);

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
    public void setup(LayerView layerView) {
        layerView.setRotate(layerView.getLayer().getRotation());
    }

    @Override
    public void configure(LayerView layerView, Timeline timeline) {
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(layerView.rotateProperty(), layerView.getLayer().getRotation(), getInterpolator())),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(layerView.rotateProperty(), getRotation(), getInterpolator())));
    }
}
