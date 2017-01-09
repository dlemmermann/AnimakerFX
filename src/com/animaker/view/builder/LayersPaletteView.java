package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.view.skins.builder.LayersPaletteViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 21.12.16.
 */
public class LayersPaletteView extends SlideControlBase {

    public LayersPaletteView() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayersPaletteViewSkin(this);
    }

    private final ObjectProperty<Layer> selectedLayer = new SimpleObjectProperty<>(this, "selectedLayer");

    public final ObjectProperty<Layer> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Layer selectedLayer) {
        this.selectedLayer.set(selectedLayer);
    }

    public final Layer getSelectedLayer() {
        return selectedLayer.get();
    }
}

