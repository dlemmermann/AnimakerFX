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
package com.animaker.view.elements;

import com.animaker.model.elements.RegionElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.beans.InvalidationListener;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

import java.io.File;

/**
 * Created by lemmi on 14.03.17.
 */
public class RegionElementView extends ElementView<RegionElement> {

    private final InvalidationListener updateRegionListener = it -> updateRegionBackground();

    public RegionElementView(SlideView slideView, RegionElement element) {
        super(slideView, element);

        element.backgroundImageFileNameProperty().addListener(updateRegionListener);
        element.repeatXProperty().addListener(updateRegionListener);
        element.repeatYProperty().addListener(updateRegionListener);

        updateRegionBackground();
    }

    private void updateRegionBackground() {
        try {
            RegionElement element = getElement();
            final String imageFileName = element.getBackgroundImageFileName();
            if (imageFileName != null) {
                File file = getPresentationView().getProject().getFile(imageFileName);
                Image image = new Image(file.toURI().toURL().toExternalForm());
                BackgroundImage backgroundImage = new BackgroundImage(image, element.getRepeatX(), element.getRepeatY(), BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true));
                setBackground(new Background(backgroundImage));
            } else {
                setBackground(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
