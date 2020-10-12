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
import javafx.util.Duration;

/**
 * Created by lemmi on 28.02.17.
 */
@XmlType(name = "play")
public class Play extends Transition {

    public Play() {
        super("Play");

        setDuration(Duration.seconds(10));
    }

    @Override
    public void setup(ElementView elementView) {
        elementView.setPlay(false);
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(elementView.playProperty(), true)),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(elementView.playProperty(), false))
        );
    }
}
