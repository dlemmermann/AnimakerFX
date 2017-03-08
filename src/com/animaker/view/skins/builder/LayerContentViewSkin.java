package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.model.Layer.LayerType;
import com.animaker.view.builder.LayerContentView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.dialog.FontSelectorDialog;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerContentViewSkin extends SkinBase<LayerContentView> {

    private Node textSettingsView;
    private Node imageSettingsView;
    private Node videoSettingsView;
    private Node codeSettingsView;
    private Node fxmlSettingsView;
    private Node htmlSettingsView;

    private TextArea textLayerText;

    public LayerContentViewSkin(LayerContentView view) {
        super(view);

        Layer layer = view.getLayer();

        textSettingsView = createTextSettings();
        videoSettingsView = createVideoSettings();
        imageSettingsView = createImageSettings();
        codeSettingsView = createNodeSettings();
        fxmlSettingsView = createFxmlSettings();
        htmlSettingsView = createWebSettings();

        layer.typeProperty().addListener(it -> {
            showSettingsView();
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().setAll(textSettingsView, imageSettingsView, videoSettingsView, codeSettingsView, fxmlSettingsView);

//        borderPane.setStyle("-fx-background-color: -fx-base;");
        getChildren().add(stackPane);

        showSettingsView();
    }

    private void showSettingsView() {
        final LayerType layerType = getSkinnable().getLayer().getType();

        switch (layerType) {
            case TEXT:
                textSettingsView.toFront();
                break;
            case CODE:
                codeSettingsView.toFront();
                break;
            case FXML:
                fxmlSettingsView.toFront();
                break;
            case HTML:
                htmlSettingsView.toFront();
                break;
            case IMAGE:
                imageSettingsView.toFront();
                break;
            case VIDEO:
                videoSettingsView.toFront();
                break;
        }
    }

    private Node createImageSettings() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        return borderPane;
    }

    private Node createVideoSettings() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        return borderPane;
    }

    private Node createWebSettings() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        return borderPane;
    }

    private Node createNodeSettings() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        return borderPane;
    }

    private Node createFxmlSettings() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        return borderPane;
    }

    private Node createTextSettings() {
        textLayerText = new TextArea();
        textLayerText.setPrefColumnCount(0);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        borderPane.setCenter(textLayerText);
        BorderPane.setMargin(textLayerText, new Insets(5, 10, 10, 10));
        return borderPane;
    }
}
