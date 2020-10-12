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

import javax.xml.bind.annotation.XmlType;

import com.animaker.model.Element;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "video")
public class VideoElement extends Element {

    public VideoElement() {
        super();
    }

    @Override
    public ElementType getType() {
        return ElementType.VIDEO;
    }

    // video content support

    private final StringProperty videoFileName = new SimpleStringProperty(this, "videoFileName");

    public final StringProperty videoFileNameProperty() {
        return videoFileName;
    }

    public final String getVideoFileName() {
        return videoFileName.get();
    }

    public final void setVideoFileName(String content) {
        this.videoFileName.set(content);
    }

    // video content support / preserve ratio

    private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio", false);

    public final BooleanProperty preserveRatioProperty() {
        return preserveRatio;
    }

    public final void setPreserveRatio(boolean preserveRatio) {
        this.preserveRatio.set(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return preserveRatio.get();
    }

}
