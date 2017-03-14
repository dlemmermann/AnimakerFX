package com.animaker.view.builder.layer;

import com.animaker.model.Layer;
import com.animaker.model.Layer.LayoutStrategy;
import com.animaker.model.Layer.Side;
import com.animaker.view.builder.Workbench;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by lemmi on 10.03.17.
 */
public class LayerPositioningView extends LayerSettingsBase {

    private ComboBox<LayoutStrategy> layoutBox;
    private TextField xLayoutField;
    private TextField yLayoutField;
    private ComboBox<Side> sideBox;
    private ComboBox<Pos> positionBox;

    public LayerPositioningView(Workbench workbench) {
        super(workbench);

        getStyleClass().add("palette");

        layoutBox.getItems().setAll(LayoutStrategy.values());
        sideBox.getItems().setAll(Side.values());
        positionBox.getItems().setAll(Pos.values());
    }

    @Override
    protected Node createContent() {
        layoutBox = new ComboBox<>();
        xLayoutField = new TextField();
        yLayoutField = new TextField();
        sideBox = new ComboBox<>();
        positionBox = new ComboBox<>();

        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.BASELINE_LEFT);
        hbox.getChildren().addAll(
                new Label("X:"), xLayoutField,
                new Label("Y:"), yLayoutField,
                new Label("Side:"), sideBox,
                new Label("Position:"), positionBox
        );

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layoutBox);
        borderPane.setCenter(hbox);

        return borderPane;
    }

    @Override
    protected void updateView(Layer oldLayer, Layer newLayer) {
        if (oldLayer != null) {
            Bindings.unbindBidirectional(layoutBox.valueProperty(), oldLayer.layoutStrategyProperty());
            Bindings.unbindBidirectional(xLayoutField.textProperty(), oldLayer.layoutXProperty());
            Bindings.unbindBidirectional(yLayoutField.textProperty(), oldLayer.layoutYProperty());
            Bindings.unbindBidirectional(sideBox.valueProperty(), oldLayer.sideProperty());
            Bindings.unbindBidirectional(positionBox.valueProperty(), oldLayer.positionProperty());
        }

        if (newLayer != null) {
            Bindings.bindBidirectional(layoutBox.valueProperty(), newLayer.layoutStrategyProperty());
            Bindings.bindBidirectional(xLayoutField.textProperty(), newLayer.layoutXProperty(), new NumberStringConverter());
            Bindings.bindBidirectional(yLayoutField.textProperty(), newLayer.layoutYProperty(), new NumberStringConverter());
            Bindings.bindBidirectional(sideBox.valueProperty(), newLayer.sideProperty());
            Bindings.bindBidirectional(positionBox.valueProperty(), newLayer.positionProperty());
        }
    }
}
