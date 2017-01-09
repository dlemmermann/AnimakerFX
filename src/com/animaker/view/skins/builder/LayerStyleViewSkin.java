package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.view.builder.LayerStyleView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerStyleViewSkin extends SkinBase<LayerStyleView> {

    private Layer layer;
    private TextArea textArea;

    public LayerStyleViewSkin(LayerStyleView view) {
        super(view);

        StackPane stackPane = new StackPane();

        textArea = new TextArea();
        stackPane.getChildren().add(textArea);
        StackPane.setMargin(textArea, new Insets(10));

        getChildren().add(stackPane);

        view.layerProperty().addListener(it -> bindView());
        bindView();
    }

    private void bindView() {
        layer = getSkinnable().getLayer();
        if (layer != null) {
            Bindings.bindBidirectional(textArea.textProperty(), layer.styleProperty());
        }
    }
}
