package com.animaker.model.elements;

import com.animaker.model.Element;
import com.animaker.model.transition.Play;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "video")
public class VideoElement extends Element {

    public VideoElement() {
        super();

        Play play = new Play();
        play.setDuration(Duration.seconds(30));
        getOpeningTransitions().add(play);
    }

    @Override
    public ElementType getType() {
        return ElementType.VIDEO;
    }

    // video content support

    private final StringProperty videoFileName = new SimpleStringProperty(this, "videoFileName");

    public final StringProperty videoFileNameProperty() {
        return videoFileName;
    }

    public final String getVideoFileName() {
        return videoFileName.get();
    }

    public final void setVideoFileName(String content) {
        this.videoFileName.set(content);
    }

    // video content support / preserve ratio

    private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio", false);

    public final BooleanProperty preserveRatioProperty() {
        return preserveRatio;
    }

    public final void setPreserveRatio(boolean preserveRatio) {
        this.preserveRatio.set(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return preserveRatio.get();
    }

}
