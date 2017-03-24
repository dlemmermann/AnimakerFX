package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.model.elements.ImageElement;
import com.animaker.view.builder.Workbench;
import com.animaker.view.builder.element.TransitionSettingsView.TransitionLocationType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by lemmi on 20.12.16.
 */
public class ElementTransitionsView extends ElementSettingsView<Element> {

    public ElementTransitionsView(Workbench workbench) {
        super(workbench);

        TransitionSettingsView openingTransition = new TransitionSettingsView(workbench, "Opening", TransitionLocationType.OPENING);
        TransitionSettingsView loopTransition = new TransitionSettingsView(workbench, "Loop", TransitionLocationType.SHOWING);
        TransitionSettingsView endingTransition = new TransitionSettingsView(workbench, "Ending", TransitionLocationType.CLOSING);
        TransitionSettingsView hoverTransition = new TransitionSettingsView(workbench, "Hover", TransitionLocationType.HOVER);
        TransitionSettingsView parallaxTransition = new TransitionSettingsView(workbench, "Parallax", TransitionLocationType.PARALLAX);

        openingTransition.elementProperty().bind(elementProperty());
        loopTransition.elementProperty().bind(elementProperty());
        endingTransition.elementProperty().bind(elementProperty());
        hoverTransition.elementProperty().bind(elementProperty());
        parallaxTransition.elementProperty().bind(elementProperty());

        HBox hBox = new HBox();
        hBox.setFillHeight(true);

        HBox.setHgrow(openingTransition, Priority.ALWAYS);
        HBox.setHgrow(loopTransition, Priority.ALWAYS);
        HBox.setHgrow(endingTransition, Priority.ALWAYS);
        HBox.setHgrow(hoverTransition, Priority.ALWAYS);
        HBox.setHgrow(parallaxTransition, Priority.ALWAYS);

        hBox.getChildren().setAll(openingTransition,
                loopTransition,
                endingTransition,
                hoverTransition,
                parallaxTransition);

        getChildren().setAll(hBox);

        elementProperty().bind(workbench.selectedElementProperty());
    }


    @Override
    protected void update(Element oldElement, Element newElement) {
        System.out.println("updated " + newElement);
    }
}
