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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "html")
public class HTMLElement extends Element {

    public HTMLElement() {
    }

    @Override
    public ElementType getType() {
        return ElementType.HTML;
    }

    // web content support

    private final StringProperty htmlContent = new SimpleStringProperty(this, "htmlContent");

    public final StringProperty htmlContentProperty() {
        return htmlContent;
    }

    public final String getHtmlContent() {
        return htmlContent.get();
    }

    public final void setHtmlContent(String content) {
        this.htmlContent.set(content);
    }
}
