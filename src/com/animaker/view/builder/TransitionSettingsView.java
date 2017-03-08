package com.animaker.view.builder;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ToggleSwitch;

import java.util.Objects;

/**
 * Created by lemmi on 20.12.16.
 */
public class TransitionSettingsView extends LayerControlBase {

    public TransitionSettingsView(Workbench workbench, String title) {
        super(workbench);

        Objects.requireNonNull(title);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        ToggleSwitch onOffSwitch = new ToggleSwitch();

        HBox header = new HBox(10);
        header.getChildren().setAll(titleLabel, onOffSwitch);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        HBox.setHgrow(onOffSwitch, Priority.NEVER);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(header);
        BorderPane.setMargin(header, new Insets(10));

        getChildren().setAll(borderPane);
    }
}
