package com.animaker.view.builder;

import com.animaker.model.Layer;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class LayerStyleView extends LayerControlBase {

    private TextArea textArea;

    public LayerStyleView(Workbench workbench) {
        super(workbench);

        textArea = new TextArea();
        getChildren().add(textArea);
        StackPane.setMargin(textArea, new Insets(10));

        layerProperty().addListener(it -> bindView());
        bindView();
    }

    private void bindView() {
        Layer layer = getLayer();
        if (layer != null) {
            Bindings.bindBidirectional(textArea.textProperty(), layer.styleProperty());
        }
    }
}
