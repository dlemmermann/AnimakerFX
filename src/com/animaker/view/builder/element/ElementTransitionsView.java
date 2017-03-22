package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.view.builder.Workbench;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by lemmi on 20.12.16.
 */
public class ElementTransitionsView extends ElementSettingsView<Element> {

    public ElementTransitionsView(Workbench workbench) {
        super(workbench);

        TransitionSettingsView openingTransition = new TransitionSettingsView(workbench, "Opening");
        TransitionSettingsView loopTransition = new TransitionSettingsView(workbench, "Loop");
        TransitionSettingsView endingTransition = new TransitionSettingsView(workbench, "Ending");
        TransitionSettingsView hoverTransition = new TransitionSettingsView(workbench, "Hover");
        TransitionSettingsView parallaxTransition = new TransitionSettingsView(workbench, "Parallax");

        Separator sep1 = new Separator(Orientation.VERTICAL);
        Separator sep2 = new Separator(Orientation.VERTICAL);
        Separator sep3 = new Separator(Orientation.VERTICAL);
        Separator sep4 = new Separator(Orientation.VERTICAL);

        HBox hBox = new HBox(10);
        hBox.setFillHeight(true);

        HBox.setHgrow(openingTransition, Priority.ALWAYS);
        HBox.setHgrow(loopTransition, Priority.ALWAYS);
        HBox.setHgrow(endingTransition, Priority.ALWAYS);
        HBox.setHgrow(hoverTransition, Priority.ALWAYS);
        HBox.setHgrow(parallaxTransition, Priority.ALWAYS);

        hBox.getChildren().setAll(openingTransition, sep1,
                loopTransition, sep2,
                endingTransition, sep3,
                hoverTransition, sep4,
                parallaxTransition);

        getChildren().setAll(hBox);

        elementProperty().bind(workbench.selectedElementProperty());
    }

    @Override
    protected void update(Element oldElement, Element newElement) {

    }
}
