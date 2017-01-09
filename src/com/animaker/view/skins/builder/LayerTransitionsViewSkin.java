package com.animaker.view.skins.builder;

import com.animaker.view.builder.LayerTransitionsView;
import com.animaker.view.builder.TransitionSettingsView;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerTransitionsViewSkin extends SkinBase<LayerTransitionsView> {

    public LayerTransitionsViewSkin(LayerTransitionsView view) {
        super(view);

        TransitionSettingsView openingTransition = new TransitionSettingsView("Opening");
        TransitionSettingsView loopTransition = new TransitionSettingsView("Loop");
        TransitionSettingsView endingTransition = new TransitionSettingsView("Ending");
        TransitionSettingsView hoverTransition = new TransitionSettingsView("Hover");
        TransitionSettingsView parallaxTransition = new TransitionSettingsView("Parallax");

        Separator sep1 = new Separator(Orientation.VERTICAL);
        Separator sep2 = new Separator(Orientation.VERTICAL);
        Separator sep3 = new Separator(Orientation.VERTICAL);
        Separator sep4 = new Separator(Orientation.VERTICAL);

        HBox hBox = new HBox(10);
        hBox.setFillHeight(true);

        HBox.setHgrow(openingTransition, Priority.ALWAYS);
        HBox.setHgrow(loopTransition, Priority.ALWAYS);
        HBox.setHgrow(endingTransition, Priority.ALWAYS);
        HBox.setHgrow(hoverTransition, Priority.ALWAYS);
        HBox.setHgrow(parallaxTransition, Priority.ALWAYS);

        hBox.getChildren().setAll(openingTransition, sep1,
                loopTransition, sep2,
                endingTransition, sep3,
                hoverTransition, sep4,
                parallaxTransition);

        getChildren().setAll(hBox);
    }
}
