package com.animaker.view.builder;

import com.animaker.view.skins.builder.TransitionSettingsViewSkin;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 20.12.16.
 */
public class TransitionSettingsView extends LayerControlBase {

    private String title;

    public TransitionSettingsView(String title) {
        this.title = title;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TransitionSettingsViewSkin(this);
    }

    public final String getTitle() {
        return title;
    }
}
