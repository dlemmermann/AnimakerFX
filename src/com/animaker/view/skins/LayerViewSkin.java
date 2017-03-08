package com.animaker.view.skins;

import com.animaker.model.Layer;
import com.animaker.view.LayerView;
import com.animaker.view.PresentationView;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;

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

        if (node != null) {
            stackPane.getChildren().add(node);
        }
    }

    private Node buildViewVideo() {
        final String videoContent = layer.getVideoFileName();
        if (videoContent != null) {
            Media media = new Media(videoContent);
            MediaPlayer player = new MediaPlayer(media);
            MediaView view = new MediaView(player);
            view.preserveRatioProperty().bind(layer.preserveRatioProperty());
            getSkinnable().playProperty().addListener(it -> {
                if (getSkinnable().isPlay()) {
                    player.play();
                } else {
                    player.stop();
                }
            });

            PresentationView presentationView = getSkinnable().getSlideView().getPresentationView();
            presentationView.statusProperty().addListener(it -> {
                switch (presentationView.getStatus()) {
                    case PAUSED:
                        player.pause();
                        break;
                    case PLAY:
                        if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                            player.play();
                        }
                        break;
                    case STOPPED:
                        player.stop();
                        break;
                }
            });

            view.fitHeightProperty().bind(stackPane.heightProperty());
            view.fitWidthProperty().bind(stackPane.widthProperty());

            return view;
        }

        return null;
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
        ImageView imageView = new ImageView();
        updateImageView(imageView);
        getSkinnable().getLayer().imageFileNameProperty().addListener(it -> updateImageView(imageView));
        return imageView;
    }

    private void updateImageView(ImageView imageView) {
        try {
            final String imageFileName = layer.getImageFileName();
            if (imageFileName != null) {
                File file = getSkinnable().getPresentationView().getProject().getFile(imageFileName);
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
