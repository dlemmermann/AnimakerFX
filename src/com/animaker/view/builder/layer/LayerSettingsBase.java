package com.animaker.view.builder.layer;

import com.animaker.model.Layer;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public abstract class LayerSettingsBase extends StackPane {

    private Workbench workbench;

    protected LayerSettingsBase(Workbench workbench) {
        this.workbench = workbench;

        layerProperty().bindBidirectional(workbench.selectedLayerProperty());

        layerProperty().addListener((observable, oldLayer, newLayer) -> updateView(oldLayer, newLayer));

        Label layerName = new Label();
        layerProperty().addListener(it -> {
            Layer layer = getLayer();
            if (layer != null) {
                layerName.setText(layer.getName());
            } else {
                layerName.setText("No layer selected...");
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layerName);
        borderPane.setCenter(createContent());

        getChildren().add(borderPane);
    }

    protected Node createContent() {
        return new Label("Missing content. Not implemented, yet.");
    }

    protected abstract void updateView(Layer oldLayer, Layer newLayer);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return getWorkbench().getProject();
    }

    private final ObjectProperty<Layer> layer = new SimpleObjectProperty<>(this, "layer");

    public final ObjectProperty<Layer> layerProperty() {
        return layer;
    }

    public final Layer getLayer() {
        return layer.get();
    }

    public final void setLayer(Layer layer) {
        this.layer.set(layer);
    }
}
