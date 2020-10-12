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
