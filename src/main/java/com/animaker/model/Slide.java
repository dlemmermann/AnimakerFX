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
package com.animaker.model;

import com.animaker.model.elements.CodeElement;
import com.animaker.model.elements.FXMLElement;
import com.animaker.model.elements.HTMLElement;
import com.animaker.model.elements.ImageElement;
import com.animaker.model.elements.RegionElement;
import com.animaker.model.elements.TextElement;
import com.animaker.model.elements.VideoElement;
import com.animaker.model.transition.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BackgroundRepeat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Slide")
public class Slide extends ModelObject {


    public Slide() {
    }

    // image content support

    private final StringProperty backgroundImageFileName = new SimpleStringProperty(this, "backgroundImageFileName");

    public final StringProperty backgroundImageFileNameProperty() {
        return backgroundImageFileName;
    }

    public final String getBackgroundImageFileName() {
        return backgroundImageFileName.get();
    }

    public final void setBackgroundImageFileName(String fileName) {
        this.backgroundImageFileName.set(fileName);
    }

    // repeat x support

    private ObjectProperty<BackgroundRepeat> repeatX = new SimpleObjectProperty<>(this, "repeat-x", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatXProperty() {
        return repeatX;
    }

    public final BackgroundRepeat getRepeatX() {
        return repeatX.get();
    }

    public void setRepeatX(BackgroundRepeat repeat) {
        this.repeatX.set(repeat);
    }

    // repeat y support

    private ObjectProperty<BackgroundRepeat> repeatY = new SimpleObjectProperty<>(this, "repeat-y", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatYProperty() {
        return repeatY;
    }

    public final BackgroundRepeat getRepeatY() {
        return repeatY.get();
    }

    public void setRepeatY(BackgroundRepeat repeat) {
        this.repeatY.set(repeat);
    }

    // transition support

    private final ObjectProperty<Transition> transition = new SimpleObjectProperty<>(this, "transition");

    public final ObjectProperty<Transition> transitionProperty() {
        return transition;
    }

    public final void setTransition(Transition transition) {
        this.transition.set(transition);
    }

    public final Transition getTransition() {
        return transition.get();
    }

    // elements support

    private ObservableList<Element> elements = FXCollections.observableArrayList();

    @XmlElementWrapper(name = "elements")
    @XmlElements({
            @XmlElement(name = "code", type = CodeElement.class),
            @XmlElement(name = "fxml", type = FXMLElement.class),
            @XmlElement(name = "html", type = HTMLElement.class),
            @XmlElement(name = "image", type = ImageElement.class),
            @XmlElement(name = "region", type = RegionElement.class),
            @XmlElement(name = "text", type = TextElement.class),
            @XmlElement(name = "video", type = VideoElement.class)
    })
    public final ObservableList<Element> getElements() {
        return elements;
    }
}
