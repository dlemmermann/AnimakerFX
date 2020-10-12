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
package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BackgroundRepeat;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "region")
public class RegionElement extends Element {

    public RegionElement() {
    }

    @Override
    public ElementType getType() {
        return ElementType.REGION;
    }

    private final StringProperty backgroundImageFileName = new SimpleStringProperty(this, "backgroundImageFileName");

    public final StringProperty backgroundImageFileNameProperty() {
        return backgroundImageFileName;
    }

    public final String getBackgroundImageFileName() {
        return backgroundImageFileName.get();
    }

    public final void setBackgroundImageFileName(String content) {
        this.backgroundImageFileName.set(content);
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
}