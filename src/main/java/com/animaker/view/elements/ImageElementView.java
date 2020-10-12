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

import java.io.File;

import com.animaker.model.elements.ImageElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by lemmi on 14.03.17.
 */
public class ImageElementView extends ElementView<ImageElement> {

    public ImageElementView(SlideView slideView, ImageElement element) {
        super(slideView, element);

        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);
        imageView.fitWidthProperty().bind(element.widthProperty());
        imageView.fitHeightProperty().bind(element.heightProperty());
        imageView.setPreserveRatio(false);

        updateImageView(imageView);
        getElement().imageFileNameProperty().addListener(it -> updateImageView(imageView));

        getChildren().add(imageView);
    }

    private void updateImageView(ImageView imageView) {
        try {
            ImageElement element = getElement();
            final String imageFileName = element.getImageFileName();
            if (imageFileName != null) {
                File file = getPresentationView().getProject().getFile(imageFileName);
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
