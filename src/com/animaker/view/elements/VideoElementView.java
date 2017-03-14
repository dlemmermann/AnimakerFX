package com.animaker.view.elements;

import com.animaker.model.elements.VideoElement;
import com.animaker.view.ElementView;
import com.animaker.view.PresentationView;
import com.animaker.view.SlideView;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

/**
 * Created by lemmi on 14.03.17.
 */
public class VideoElementView extends ElementView<VideoElement> {

    public VideoElementView(SlideView slideView, VideoElement element) {
        super(slideView, element);

        try {
            final String videoFileName = element.getVideoFileName();
            if (videoFileName != null) {
                File file = getPresentationView().getProject().getFile(videoFileName);
                Media media = new Media(file.toURI().toURL().toExternalForm());
                MediaPlayer player = new MediaPlayer(media);
                MediaView view = new MediaView(player);
                view.preserveRatioProperty().bind(element.preserveRatioProperty());
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

                getChildren().add(view);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
