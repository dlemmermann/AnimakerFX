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

import com.animaker.model.elements.TextElement;
import com.animaker.view.builder.Workbench;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Created by lemmi on 14.03.17.
 */
public class TextSettingsView extends ElementSettingsView<TextElement> {

    private final TextArea textArea;

    public TextSettingsView(Workbench workbench) {
        super(workbench);

        textArea = new TextArea();
        textArea.setPrefColumnCount(0);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        BorderPane.setMargin(textArea, new Insets(5, 10, 10, 10));

        workbench.selectedElementProperty().addListener(it -> {
            if (workbench.getSelectedElement() instanceof TextElement) {
                setElement((TextElement) workbench.getSelectedElement());
            }
        });
    }

    @Override
    protected void update(TextElement oldElement, TextElement newElement) {
        textArea.textProperty().bindBidirectional(newElement.textProperty());
    }
}
