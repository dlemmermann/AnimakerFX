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
