package com.animaker.model.transition;

import com.animaker.view.ElementView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 28.02.17.
 */
@XmlType(name = "play")
public class Play extends Transition {

    public Play() {
        super("Play");

        setDuration(Duration.seconds(10));
    }

    @Override
    public void setup(ElementView elementView) {
        elementView.setPlay(false);
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(elementView.playProperty(), true)),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(elementView.playProperty(), false))
        );
    }
}
