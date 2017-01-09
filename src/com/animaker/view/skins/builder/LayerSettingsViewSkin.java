package com.animaker.view.skins.builder;

import com.animaker.view.builder.LayerContentView;
import com.animaker.view.builder.LayerSettingsView;
import com.animaker.view.builder.LayerStyleView;
import com.animaker.view.builder.LayerTransitionsView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerSettingsViewSkin extends SkinBase<LayerSettingsView> {

    public LayerSettingsViewSkin(LayerSettingsView view) {
        super(view);

        LayerContentView contentView = new LayerContentView();
        contentView.layerProperty().bind(view.layerProperty());

        LayerStyleView styleView = new LayerStyleView();
        styleView.layerProperty().bind(view.layerProperty());

        LayerTransitionsView transitionsView = new LayerTransitionsView();
        transitionsView.layerProperty().bind(view.layerProperty());

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        Tab contentTab = new Tab("Content", contentView);
        Tab styleTab = new Tab("Style", styleView);
        Tab transitionsTab = new Tab("Transitions", transitionsView);
        tabPane.getTabs().setAll(contentTab, styleTab, transitionsTab);
        tabPane.getTabs().forEach(tab -> tab.setClosable(false));

        getChildren().setAll(tabPane);

        view.disableProperty().bind(Bindings.isNull(view.layerProperty()));
    }
}
