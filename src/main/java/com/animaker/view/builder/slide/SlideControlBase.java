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
package com.animaker.view.builder.slide;

import com.animaker.model.Slide;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

public abstract class SlideControlBase extends StackPane {

    private Workbench workbench;

    protected SlideControlBase(Workbench workbench) {
        this.workbench = workbench;

        slideProperty().bind(workbench.selectedSlideProperty());

        slideProperty().addListener((observable, oldSlide, newSlide) -> updateSlide(oldSlide, newSlide));
    }

    protected abstract void updateSlide(Slide oldSlide, Slide newSlide);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return getWorkbench().getProject();
    }

    private final ObjectProperty<Slide> slide = new SimpleObjectProperty<>(this, "slide");

    public final ObjectProperty<Slide> slideProperty() {
        return slide;
    }

    public final Slide getSlide() {
        return slide.get();
    }

    public final void setSlide(Slide slide) {
        this.slide.set(slide);
    }
}
