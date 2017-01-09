package com.animaker.view.skins.builder;

import com.animaker.view.builder.TransitionSettingsView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ToggleSwitch;

public class TransitionSettingsViewSkin extends SkinBase<TransitionSettingsView> {

    public TransitionSettingsViewSkin(TransitionSettingsView view) {
        super(view);

        Label title = new Label(view.getTitle());
        title.setStyle("-fx-font-weight: bold;");
        title.setMaxWidth(Double.MAX_VALUE);

        ToggleSwitch onOffSwitch = new ToggleSwitch();

        HBox header = new HBox(10);
        header.getChildren().setAll(title, onOffSwitch);
        HBox.setHgrow(title, Priority.ALWAYS);
        HBox.setHgrow(onOffSwitch, Priority.NEVER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(header);
        BorderPane.setMargin(header, new Insets(10));

        getChildren().setAll(borderPane);
    }
}
