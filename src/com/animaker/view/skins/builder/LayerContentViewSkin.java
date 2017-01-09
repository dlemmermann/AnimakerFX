package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.view.builder.LayerContentView;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.dialog.FontSelectorDialog;

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerContentViewSkin extends SkinBase<LayerContentView> {

    private FontSelectorDialog fontSelectorDialog;

    private ToggleButton imageTypeButton;
    private ToggleButton textTypeButton;
    private ToggleButton videoTypeButton;
    private ToggleButton codeTypeButton;
    private ToggleButton fxmlTypeButton;
    private ToggleButton htmlTypeButton;

    private Node textSettingsView;
    private Node imageSettingsView;
    private Node videoSettingsView;
    private Node codeSettingsView;
    private Node fxmlSettingsView;
    private Node htmlSettingsView;

    private TextArea textLayerText;
    private Layer layer;

    public LayerContentViewSkin(LayerContentView view) {
        super(view);

        imageTypeButton = new ToggleButton("Image");
        textTypeButton = new ToggleButton("Text");
        videoTypeButton = new ToggleButton("Video");
        codeTypeButton = new ToggleButton("Code");
        fxmlTypeButton = new ToggleButton("FXML");
        htmlTypeButton = new ToggleButton("HTML");

        textSettingsView = createTextSettings();
        videoSettingsView = createVideoSettings();
        imageSettingsView = createImageSettings();
        codeSettingsView = createNodeSettings();
        fxmlSettingsView = createFxmlSettings();
        htmlSettingsView = createWebSettings();

        textTypeButton.setOnAction(evt -> textSettingsView.toFront());
        videoTypeButton.setOnAction(evt -> videoSettingsView.toFront());
        imageTypeButton.setOnAction(evt -> imageSettingsView.toFront());
        codeTypeButton.setOnAction(evt -> codeSettingsView.toFront());
        fxmlTypeButton.setOnAction(evt -> fxmlSettingsView.toFront());
        htmlTypeButton.setOnAction(evt -> htmlSettingsView.toFront());

        SegmentedButton selector = new SegmentedButton();
        selector.getToggleGroup().getToggles().setAll(imageTypeButton, textTypeButton, videoTypeButton, codeTypeButton, fxmlTypeButton, htmlTypeButton);
        selector.getButtons().setAll(imageTypeButton, textTypeButton, videoTypeButton, codeTypeButton, fxmlTypeButton, htmlTypeButton);
        selector.getToggleGroup().selectedToggleProperty().addListener(it -> {

            final ToggleButton button = (ToggleButton) selector.getToggleGroup().getSelectedToggle();
            final Layer layer = view.getLayer();

            if (layer != null) {
                if (button == textTypeButton) {
                    layer.setType(Layer.LayerType.TEXT);
                } else if (button == imageTypeButton) {
                    layer.setType(Layer.LayerType.IMAGE);
                } else if (button == videoTypeButton) {
                    layer.setType(Layer.LayerType.VIDEO);
                } else if (button == codeTypeButton) {
                    layer.setType(Layer.LayerType.CODE);
                } else if (button == fxmlTypeButton) {
                    layer.setType(Layer.LayerType.FXML);
                } else if (button == htmlTypeButton) {
                    layer.setType(Layer.LayerType.HTML);
                } else {
                    throw new UnsupportedOperationException("unsupported type / button was pressed");
                }
            }
        });

        BorderPane.setAlignment(selector, Pos.CENTER);
        BorderPane.setMargin(selector, new Insets(10));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().setAll(textSettingsView, imageSettingsView, videoSettingsView, codeSettingsView, fxmlSettingsView);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(selector);
        borderPane.setCenter(stackPane);
        borderPane.setStyle("-fx-background-color: -fx-base;");
        getChildren().add(borderPane);

        view.layerProperty().addListener(it -> updateSettings(view.getLayer()));
        updateSettings(view.getLayer());
    }

    private void updateSettings(Layer layer) {
        if (this.layer != null) {
            Bindings.unbindBidirectional(textLayerText.textProperty(), this.layer.textContentProperty());
        }

        layer = getSkinnable().getLayer();

        if (layer != null) {
            Bindings.bindBidirectional(textLayerText.textProperty(), layer.textContentProperty());
            switch (layer.getType()) {
                case CODE:
                    codeTypeButton.setSelected(true);
                    codeSettingsView.toFront();
                    break;
                case FXML:
                    fxmlTypeButton.setSelected(true);
                    fxmlSettingsView.toFront();
                    break;
                case HTML:
                    htmlTypeButton.setSelected(true);
                    htmlSettingsView.toFront();
                    break;
                case TEXT:
                    textTypeButton.setSelected(true);
                    textSettingsView.toFront();
                    break;
                case IMAGE:
                    imageTypeButton.setSelected(true);
                    imageSettingsView.toFront();
                    break;
                case VIDEO:
                    videoTypeButton.setSelected(true);
                    videoSettingsView.toFront();
                    break;
            }
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

        Button fontButton = new Button("Font ...");
        fontButton.setOnAction(evt -> {
            if (fontSelectorDialog == null) {
                fontSelectorDialog = new FontSelectorDialog(textLayerText.getFont());
                fontSelectorDialog.initOwner(getSkinnable().getScene().getWindow());
            }

            fontSelectorDialog.showAndWait().ifPresent(font -> textLayerText.setFont(font));
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: -fx-base;");
        borderPane.setCenter(textLayerText);
        borderPane.setBottom(fontButton);
        BorderPane.setAlignment(fontButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(textLayerText, new Insets(5, 10, 10, 10));
        BorderPane.setMargin(fontButton, new Insets(0, 10, 10, 10));

        return borderPane;
    }
}
