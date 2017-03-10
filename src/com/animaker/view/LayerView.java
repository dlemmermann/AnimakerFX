package com.animaker.view;

import com.animaker.model.Layer;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

/**
 * Created by lemmi on 20.12.16.
 */
public class LayerView extends StackPane {

    private SlideView slideView;
    private final Layer layer;

    private double mouseX;
    private double mouseY;

    public LayerView(SlideView slideView, Layer layer) {
        this.slideView = slideView;
        this.layer = layer;

        getStyleClass().add("layer");

        visibleProperty().bind(layer.visibleProperty());

        setFocusTraversable(true);

        layoutXProperty().bindBidirectional(layer.layoutXProperty());
        layoutYProperty().bindBidirectional(layer.layoutYProperty());

        layer.typeProperty().addListener(it -> buildView());
        buildView();
    }

    public final PresentationView getPresentationView() {
        return slideView.getPresentationView();
    }

    public final SlideView getSlideView() {
        return slideView;
    }

    public final Layer getLayer() {
        return layer;
    }

    // play support (for media)

    private final BooleanProperty play = new SimpleBooleanProperty(this, "play", false);

    public final BooleanProperty playProperty() {
        return play;
    }

    public final void setPlay(boolean play) {
        this.play.set(play);
    }

    public final boolean isPlay() {
        return play.get();
    }

    public final void setupAnimation() {
        layer.getOpeningTransitions().forEach(transition -> transition.setup(this));
    }

    public final void configureAnimation(Timeline timeline) {
        layer.getOpeningTransitions().forEach(transition -> transition.configure(this, timeline));
    }

    private void buildView() {
        getChildren().clear();

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
            node.styleProperty().bindBidirectional(layer.styleProperty());
            node.setMouseTransparent(true);
            getChildren().add(node);
        }
    }

    private Node buildViewVideo() {
        final String videoContent = layer.getVideoFileName();
        if (videoContent != null) {
            Media media = new Media(videoContent);
            MediaPlayer player = new MediaPlayer(media);
            MediaView view = new MediaView(player);
            view.preserveRatioProperty().bind(layer.preserveRatioProperty());
            playProperty().addListener(it -> {
                if (isPlay()) {
                    player.play();
                } else {
                    player.stop();
                }
            });

            PresentationView presentationView = getSlideView().getPresentationView();
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

            view.fitHeightProperty().bind(heightProperty());
            view.fitWidthProperty().bind(widthProperty());

            return view;
        }

        return null;
    }

    private Node buildViewWeb() {
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.setUserStyleSheetLocation(LayerView.class.getResource("style.css").toExternalForm());
        engine.load(layer.getHtmlContent());
        view.prefWidthProperty().bind(widthProperty());
        view.prefHeightProperty().bind(heightProperty());
        return view;
    }

    private Node buildViewFxml() {
        return null;
    }

    private Node buildViewImage() {
        ImageView imageView = new ImageView();
        updateImageView(imageView);
        getLayer().imageFileNameProperty().addListener(it -> updateImageView(imageView));
        return imageView;
    }

    private void updateImageView(ImageView imageView) {
        try {
            final String imageFileName = layer.getImageFileName();
            if (imageFileName != null) {
                File file = getPresentationView().getProject().getFile(imageFileName);
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
