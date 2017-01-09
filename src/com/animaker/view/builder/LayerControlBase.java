package com.animaker.view.builder;

import com.animaker.model.Layer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;

public abstract class LayerControlBase extends Control {

    protected LayerControlBase() {
        setStyle("-fx-background-color: white;");
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
