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
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 14.03.17.
 */
public abstract class ElementSettingsView<T extends Element> extends StackPane {

    private Workbench workbench;

    protected ElementSettingsView(Workbench workbench) {
        this.workbench = workbench;

        elementProperty().addListener((observable, oldElement, newElement) -> update(oldElement, newElement));

        setStyle("-fx-background-color: -fx-base;");
    }

    protected abstract void update(T oldElement, T newElement);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return workbench.getProject();
    }

    // element support

    private final ObjectProperty<T> element = new SimpleObjectProperty<>(this, "element");

    public final ObjectProperty<T> elementProperty() {
        return element;
    }

    public final T getElement() {
        return element.get();
    }

    public final void setElement(T element) {
        this.element.set(element);
    }
}
