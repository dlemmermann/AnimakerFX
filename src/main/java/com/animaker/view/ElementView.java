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
package com.animaker.view;

import com.animaker.model.Element;
import com.animaker.model.Element.LayoutStrategy;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 20.12.16.
 */
public abstract class ElementView<T extends Element> extends StackPane {

    private SlideView slideView;
    private final T element;

    public ElementView(SlideView slideView, T element) {
        this.slideView = slideView;
        this.element = element;

        getStyleClass().add("element");

        visibleProperty().bind(element.visibleProperty());

        setFocusTraversable(true);

        layoutXProperty().bindBidirectional(element.layoutXProperty());
        layoutYProperty().bindBidirectional(element.layoutYProperty());

        managedProperty().bind(element.layoutStrategyProperty().isEqualTo(LayoutStrategy.ABSOLUTE));

        styleProperty().bindBidirectional(element.styleProperty());

        prefWidthProperty().bind(element.widthProperty());
        prefHeightProperty().bind(element.heightProperty());
    }

    public final PresentationView getPresentationView() {
        return slideView.getPresentationView();
    }

    public final SlideView getSlideView() {
        return slideView;
    }

    public final T getElement() {
        return element;
    }

    public final void reset() {
        Element element = getElement();
        setLayoutX(element.getLayoutX());
        setLayoutY(element.getLayoutY());
        setScaleX(element.getScaleX());
        setScaleY(element.getScaleY());
        setRotate(element.getRotation());
        setOpacity(element.getOpacity());
        setTranslateX(0);
        setTranslateY(0);
        resize(element.getWidth(), element.getHeight());
    }

    public final void setupAnimation() {
        element.getOpeningTransitions().forEach(transition -> {
            if (transition.isEnabled()) {
                transition.setup(this);
            }
        });
    }

    public final void configureAnimation(Timeline timeline) {
        element.getOpeningTransitions().forEach(transition -> {
            if (transition.isEnabled()) {
                transition.configure(this, timeline);
            }
        });
    }

    // play support (for media)

    private final BooleanProperty play = new SimpleBooleanProperty(this, "play", false);

    public final BooleanProperty playProperty() {
        return play;
    }

    public final void setPlay(boolean play) {
        this.play.set(play);
    }

    public final boolean isPlay() {
        return play.get();
    }
}
