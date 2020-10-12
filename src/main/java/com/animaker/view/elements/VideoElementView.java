/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.animaker.view.elements;

import com.animaker.model.elements.VideoElement;
import com.animaker.view.ElementView;
import com.animaker.view.PresentationView;
import com.animaker.view.SlideView;
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
                view.setMouseTransparent(true);
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

                view.fitHeightProperty().bind(element.heightProperty());
                view.fitWidthProperty().bind(element.widthProperty());

                getChildren().add(view);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
