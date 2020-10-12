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
package com.animaker.view.builder.element;

import java.io.File;

import com.animaker.model.Project;
import com.animaker.model.elements.ImageElement;
import com.animaker.view.builder.Workbench;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ImageSettingsView extends ElementSettingsView<ImageElement> {

    private ImageView imageView;

    public ImageSettingsView(Workbench workbench) {
        super(workbench);

        setStyle("-fx-background-color: -fx-base;");

        BorderPane borderPane = new BorderPane();

        imageView = new ImageView();
        imageView.setFitWidth(128);
        imageView.setFitHeight(128);
        imageView.setPreserveRatio(true);
        borderPane.setCenter(imageView);

        Button loadButton = new Button("Load");
        loadButton.setOnAction(evt -> loadImage());

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(evt -> removeImage());

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(loadButton, removeButton);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setBottom(hBox);

        updateImage();

        getChildren().add(borderPane);

        workbench.selectedElementProperty().addListener(it -> {
            if (workbench.getSelectedElement() instanceof ImageElement) {
                setElement((ImageElement) workbench.getSelectedElement());
            }
        });
    }

    final InvalidationListener listenToImageListener = it -> updateImage();

    final WeakInvalidationListener weakListenToImageListener = new WeakInvalidationListener(listenToImageListener);

    @Override
    protected void update(ImageElement oldElement, ImageElement newElement) {
        ImageElement element = getElement();
        if (element != null) {
            newElement.imageFileNameProperty().removeListener(weakListenToImageListener);
            newElement.imageFileNameProperty().addListener(weakListenToImageListener);
        }
    }

    public void updateImage() {
        ImageElement element = getElement();
        if (element != null) {
            String fileName = element.getImageFileName();
            if (fileName != null) {
                Project project = getProject();
                Image image = project.getImage(fileName);
                imageView.setImage(image);
            }
        }
    }

    private void removeImage() {
        getElement().setImageFileName(null);
        imageView.setImage(null);
    }

    private FileChooser imageFileChooser;

    private void loadImage() {
        if (imageFileChooser == null) {
            imageFileChooser = new FileChooser();
        }

        imageFileChooser.setTitle("Select Image");
        final File file = imageFileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            try {

                // all resource files have to be copied to the project base directory
                getProject().addFile(file);

                ImageElement element = getElement();
                element.setImageFileName(file.getName());

                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(image);
                getElement().setImageFileName(file.getName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            imageView.setImage(null);
        }
    }
}
