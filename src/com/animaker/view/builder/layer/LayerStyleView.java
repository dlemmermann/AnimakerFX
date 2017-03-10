package com.animaker.view.builder.layer;

import com.animaker.model.Layer;
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
    protected void updateView(Layer oldLayer, Layer newLayer) {
        if (oldLayer != null) {
            Bindings.unbindBidirectional(textArea.textProperty(), oldLayer.styleProperty());
        }

        if (newLayer != null) {
            Bindings.bindBidirectional(textArea.textProperty(), newLayer.styleProperty());
        }
    }
}
