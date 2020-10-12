/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.animaker.model.transition;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.animaker.model.ModelObject;
import com.animaker.model.util.DurationXMLAdapter;
import com.animaker.model.util.InterpolatorXMLAdapter;
import com.animaker.view.ElementView;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * Created by lemmi on 19.12.16.
 */
public abstract class Transition extends ModelObject {

    public enum TransitionType {
        BLUR,
        DRIFT,
        FADE_IN,
        FADE_OUT,
        MOVE_IN,
        MOVE_OUT,
        PARALLAX,
        PLAY,
        ROTATE,
        SCALE
    }

    public Transition() {
    }

    public Transition(String name) {
        setName(name);
    }

    // enabled support

    private final BooleanProperty enabled = new SimpleBooleanProperty(this, "enabled", true);

    public final BooleanProperty enabledProperty() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    // delay support

    private final ObjectProperty<Duration> delay = new SimpleObjectProperty<>(this, "delay", Duration.ZERO);

    public final ObjectProperty<Duration> delayProperty() {
        return delay;
    }

    public final void setDelay(Duration delay) {
        this.delay.set(delay);
    }

    @XmlJavaTypeAdapter(DurationXMLAdapter.class)
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

    @XmlJavaTypeAdapter(DurationXMLAdapter.class)
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

    @XmlJavaTypeAdapter(InterpolatorXMLAdapter.class)
    public final Interpolator getInterpolator() {
        return interpolator.get();
    }

    public void setup(ElementView elementView) {
        System.out.println("setup() for transition " + getName() + " not implemented, yet.");
    }

    public void configure(ElementView elementView, Timeline timeline) {
        System.out.println("configure() for transition " + getName() + " not implemented, yet.");
    }
}
