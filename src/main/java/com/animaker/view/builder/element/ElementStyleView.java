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

import com.animaker.model.Element;
import com.animaker.view.builder.Workbench;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class ElementStyleView extends ElementSettingsView<Element> {

    private TextArea textArea;

    public ElementStyleView(Workbench workbench) {
        super(workbench);

        textArea = new TextArea();
        StackPane.setMargin(textArea, new Insets(10));
        getChildren().add(textArea);

        elementProperty().bind(workbench.selectedElementProperty());
    }


    @Override
    protected void update(Element oldElement, Element newElement) {
        if (oldElement != null) {
            Bindings.unbindBidirectional(textArea.textProperty(), oldElement.styleProperty());
        }

        if (newElement != null) {
            Bindings.bindBidirectional(textArea.textProperty(), newElement.styleProperty());
        }
    }
}
