package com.animaker.view.builder.layer;

import com.animaker.view.builder.Workbench;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by lemmi on 10.03.17.
 */
public class LayerSettingsTabPane extends TabPane {

    public LayerSettingsTabPane(Workbench workbench) {
        getTabs().setAll(
                new Tab("Positioning", new LayerPositioningView(workbench)),
                new Tab("Style", new LayerStyleView(workbench)),
                new Tab("Transitions", new LayerTransitionsView(workbench)),
                new Tab("Timeline", new LayerTimelineView(workbench))
        );

        getTabs().forEach(tab -> tab.setClosable(false));
    }
}
