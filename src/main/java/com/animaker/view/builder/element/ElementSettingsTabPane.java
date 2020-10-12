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

import com.animaker.view.builder.Workbench;
import com.animaker.view.builder.slide.SlideTimelineView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by lemmi on 10.03.17.
 */
public class ElementSettingsTabPane extends TabPane {

    public ElementSettingsTabPane(Workbench workbench) {
        getTabs().setAll(
                new Tab("Positioning", new ElementPositioningView(workbench)),
                new Tab("Style", new ElementStyleView(workbench)),
                new Tab("Transitions", new ElementTransitionsView(workbench)),
                new Tab("Timeline", new SlideTimelineView(workbench))
        );

        getTabs().forEach(tab -> tab.setClosable(false));
    }
}
