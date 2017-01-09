package com.animaker.view.skins;

import com.animaker.model.Layer;
import com.animaker.view.LayerView;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class LayerViewSkin extends SkinBase<LayerView> {

    private final Layer layer;
    private final StackPane stackPane;

    public LayerViewSkin(LayerView view) {
        super(view);

        stackPane = new StackPane();
        getChildren().add(stackPane);

        layer = view.getLayer();
        layer.typeProperty().addListener(it -> buildView());
        buildView();
    }

    private void buildView() {
        stackPane.getChildren().clear();

        Node node;

        switch (layer.getType()) {
            case TEXT:
                node = buildViewText();
                break;
            case CODE:
                node = buildViewCode();
                break;
            case FXML:
                node = buildViewFxml();
                break;
            case IMAGE:
                node = buildViewImage();
                break;
            case VIDEO:
                node = buildViewVideo();
                break;
            case HTML:
                node = buildViewWeb();
                break;
            default:
                node = new Label("Unsupported layer type: " + layer.getType());
                break;
        }

        stackPane.getChildren().add(node);
    }

    private Node buildViewVideo() {
        Media media = new Media(layer.getVideoContent());
        MediaPlayer player = new MediaPlayer(media);
        MediaView view = new MediaView(player);
        view.preserveRatioProperty().bind(layer.preserveRatioProperty());
        view.sceneProperty().addListener(it -> {
            if (view.getScene() != null && layer.isVisible()) {
                final Duration totalDuration = player.getTotalDuration();
                player.play();
                player.seek(totalDuration.divide(2));
                player.pause();
            }
        });

        layer.visibleProperty().addListener(it -> {
            if (layer.isVisible()) {
                player.play();
            } else {
                player.pause();
            }
        });

        view.fitHeightProperty().bind(stackPane.heightProperty());
        view.fitWidthProperty().bind(stackPane.widthProperty());

        return view;
    }

    private Node buildViewWeb() {
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.setUserStyleSheetLocation(LayerViewSkin.class.getResource("style.css").toExternalForm());
        engine.load(layer.getHtmlContent());
        view.prefWidthProperty().bind(stackPane.widthProperty());
        view.prefHeightProperty().bind(stackPane.heightProperty());
        return view;
    }

    private Node buildViewFxml() {
        return null;
    }

    private Node buildViewImage() {
        ImageView image = new ImageView();
        image.imageProperty().bind(layer.imageContentProperty());
        return image;
    }

    private Node buildViewCode() {
        return null;
    }

    private Node buildViewText() {
        Text text = new Text();
        text.textProperty().bind(layer.textContentProperty());
        text.styleProperty().bind(layer.styleProperty());
        Bindings.bindContent(text.getStyleClass(), layer.getStyleClass());
        return text;
    }
}
