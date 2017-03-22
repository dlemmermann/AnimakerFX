package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.view.builder.Workbench;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
