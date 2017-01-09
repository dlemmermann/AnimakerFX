package com.animaker.model.transition;

import com.animaker.view.LayerView;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Created by lemmi on 19.12.16.
 */
public abstract class Transition {

    private String name;

    public Transition(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public final String getName() {
        return name;
    }

    private final ObjectProperty<Duration> delay = new SimpleObjectProperty<>(this, "delay", Duration.ZERO);

    public final ObjectProperty<Duration> delayProperty() {
        return delay;
    }

    public final void setDelay(Duration delay) {
        this.delay.set(delay);
    }

    public final Duration getDelay() {
        return delay.get();
    }

    // duration support

    private final ObjectProperty<Duration> duration = new SimpleObjectProperty<>(this, "duration", Duration.seconds(1));

    public final ObjectProperty<Duration> durationProperty() {
        return duration;
    }

    public final void setDuration(Duration delay) {
        this.duration.set(delay);
    }

    public final Duration getDuration() {
        return duration.get();
    }

    private final ObjectProperty<Interpolator> interpolator = new SimpleObjectProperty<>(this, "interpolator", Interpolator.EASE_BOTH);

    public final ObjectProperty<Interpolator> interpolatorProperty() {
        return interpolator;
    }

    public final void setInterpolator(Interpolator interpolator) {
        this.interpolator.set(interpolator);
    }

    public final Interpolator getInterpolator() {
        return interpolator.get();
    }

    public void setup(LayerView layerView) {
        System.out.println("setup() for transition " + getName() + " not implemented, yet.");
    }

    public void configure(LayerView layerView, Timeline timeline) {
        System.out.println("configure() for transition " + getName() + " not implemented, yet.");
    }
}
