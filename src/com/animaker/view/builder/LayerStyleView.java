package com.animaker.view.builder;

import com.animaker.view.skins.builder.LayerStyleViewSkin;
import javafx.scene.control.Skin;

public class LayerStyleView extends LayerControlBase {

    public LayerStyleView(Workbench workbench) {
        super(workbench);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayerStyleViewSkin(this);
    }
}
