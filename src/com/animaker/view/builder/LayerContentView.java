package com.animaker.view.builder;

import com.animaker.view.skins.builder.LayerContentViewSkin;
import javafx.scene.control.Skin;

public class LayerContentView extends LayerControlBase {

    public LayerContentView(Workbench workbench) {
        super(workbench);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayerContentViewSkin(this);
    }
}
