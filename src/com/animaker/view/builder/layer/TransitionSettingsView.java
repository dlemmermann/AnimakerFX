package com.animaker.view.builder.layer;

import com.animaker.model.Layer;
import com.animaker.view.builder.Workbench;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ToggleSwitch;

import java.util.Objects;

/**
 * Created by lemmi on 20.12.16.
 */
public class TransitionSettingsView extends LayerSettingsBase {

    private String title;

    public TransitionSettingsView(Workbench workbench, String title) {
        super(workbench);

        this.title = Objects.requireNonNull(title);
    }

    @Override
    protected Node createContent() {
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

        return borderPane;
    }

    @Override
    protected void updateView(Layer oldLayer, Layer newLayer) {
    }
}
