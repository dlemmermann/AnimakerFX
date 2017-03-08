package com.animaker.view.builder;

import com.animaker.view.skins.builder.LayerTransitionsViewSkin;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerTransitionsView extends LayerControlBase {

    public LayerTransitionsView(Workbench workbench) {
        super(workbench);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new LayerTransitionsViewSkin(this);
    }
}
