package com.animaker.view.builder;

import com.animaker.view.skins.builder.LayerSettingsViewSkin;
import javafx.scene.control.Skin;

public class LayerSettingsView extends LayerControlBase {

    public LayerSettingsView() {
        setStyle("-fx-background-color: white;");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayerSettingsViewSkin(this);
    }
}
