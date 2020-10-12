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
import javafx.beans.property.SimpleDoubleProperty;

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
