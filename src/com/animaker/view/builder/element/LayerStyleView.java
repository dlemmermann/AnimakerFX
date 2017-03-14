package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.view.builder.Workbench;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class LayerStyleView extends LayerSettingsBase {

    private TextArea textArea;

    public LayerStyleView(Workbench workbench) {
        super(workbench);
    }

    @Override
    protected Node createContent() {
        textArea = new TextArea();
        StackPane.setMargin(textArea, new Insets(10));
        return textArea;
    }

    @Override
    protected void updateView(Element oldElement, Element newElement) {
        if (oldElement != null) {
            Bindings.unbindBidirectional(textArea.textProperty(), oldElement.styleProperty());
        }

        if (newElement != null) {
            Bindings.bindBidirectional(textArea.textProperty(), newElement.styleProperty());
        }
    }
}
