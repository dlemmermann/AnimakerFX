package com.animaker.model.transition;

import com.animaker.view.LayerView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by lemmi on 28.02.17.
 */
public class Play extends Transition {

    public Play() {
        super("Play");

        setDuration(Duration.seconds(10));
    }

    @Override
    public void setup(LayerView layerView) {
        layerView.setPlay(false);
    }

    @Override
    public void configure(LayerView layerView, Timeline timeline) {
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(layerView.playProperty(), true)),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(layerView.playProperty(), false))
        );
    }
}
