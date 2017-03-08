package com.animaker.view.builder;

import com.animaker.model.Layer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;

public abstract class LayerControlBase extends StackPane {

    private Workbench workbench;

    protected LayerControlBase(Workbench workbench) {
        setStyle("-fx-background-color: white;");
        this.workbench = workbench;
    }

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
