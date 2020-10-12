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

import com.animaker.model.elements.TextElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.beans.binding.Bindings;
import javafx.scene.text.Text;

/**
 * Created by lemmi on 14.03.17.
 */
public class TextElementView extends ElementView<TextElement> {

    public TextElementView(SlideView slideView, TextElement element) {
        super(slideView, element);

        Text text = new Text();
        text.textProperty().bind(element.textProperty());
        text.styleProperty().bind(element.styleProperty());
        Bindings.bindContent(text.getStyleClass(), element.getStyleClass());
        getChildren().add(text);
    }
}
