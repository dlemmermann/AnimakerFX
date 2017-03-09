package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.model.Layer.LayerType;
import com.animaker.model.Project;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class LayerContentView extends LayerControlBase {

    private Node textSettingsView;
    private ImageSettingsView imageSettingsView;
    private Node videoSettingsView;
    private Node codeSettingsView;
    private Node fxmlSettingsView;
    private Node htmlSettingsView;
    private TextArea textLayerText;

    public LayerContentView(Workbench workbench) {
        super(workbench);

        textSettingsView = createTextSettings();
        videoSettingsView = createVideoSettings();
        imageSettingsView = new ImageSettingsView();
        codeSettingsView = createNodeSettings();
        fxmlSettingsView = createFxmlSettings();
        htmlSettingsView = createWebSettings();

        layerProperty().addListener(it -> {
            listenToLayer();
            showSettingsView();
            imageSettingsView.updateImage();
        });

        listenToLayer();

        getChildren().setAll(textSettingsView, imageSettingsView, videoSettingsView, codeSettingsView, fxmlSettingsView);

        showSettingsView();
    }

    final InvalidationListener showSettingsListener = it -> showSettingsView();
    final InvalidationListener listenToImageListener = it -> imageSettingsView.updateImage();

    private void listenToLayer() {
        Layer layer = getLayer();
        if (layer != null) {
            layer.typeProperty().removeListener(showSettingsListener);
            layer.typeProperty().addListener(showSettingsListener);

            layer.imageFileNameProperty().removeListener(listenToImageListener);
            layer.imageFileNameProperty().addListener(listenToImageListener);
        }
    }

    private void showSettingsView() {
        final Layer layer = getLayer();

        if (layer != null) {
            final LayerType layerType = layer.getType();

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
    }

    private class ImageSettingsView extends BorderPane {

        private ImageView imageView;

        public ImageSettingsView() {
            setStyle("-fx-background-color: -fx-base;");

            imageView = new ImageView();
            imageView.setFitWidth(128);
            imageView.setFitHeight(128);
            imageView.setPreserveRatio(true);
            setCenter(imageView);

            Button loadButton = new Button("Load");
            loadButton.setOnAction(evt -> loadImage());

            Button removeButton = new Button("Remove");
            removeButton.setOnAction(evt -> removeImage());

            HBox hBox = new HBox(10);
            hBox.getChildren().addAll(loadButton, removeButton);
            hBox.setAlignment(Pos.CENTER);

            setBottom(hBox);

            updateImage();
        }

        public void updateImage() {
            Layer layer = getLayer();
            if (layer != null) {
                String fileName = layer.getImageFileName();
                if (fileName != null) {
                    Project project = getProject();
                    Image image = project.getImage(fileName);
                    imageView.setImage(image);
                }
            }
        }

        private void removeImage() {
            getLayer().setImageFileName(null);
            imageView.setImage(null);
        }

        private FileChooser imageFileChooser;

        private void loadImage() {
            if (imageFileChooser == null) {
                imageFileChooser = new FileChooser();
            }

            imageFileChooser.setTitle("Select Image");
            final File file = imageFileChooser.showOpenDialog(getScene().getWindow());
            if (file != null) {
                try {

                    // all resource files have to be copied to the project base directory
                    getProject().addFile(file);

                    Layer layer = getLayer();
                    layer.setImageFileName(file.getName());

                    Image image = new Image(file.toURI().toURL().toExternalForm());
                    imageView.setImage(image);
                    getLayer().setImageFileName(file.getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                imageView.setImage(null);
            }
        }
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
