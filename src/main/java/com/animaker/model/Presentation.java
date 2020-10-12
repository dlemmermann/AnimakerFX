package com.animaker.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BackgroundRepeat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "presentation")
@XmlType(namespace = "com.dlsc.animation", propOrder = {
        "layout", "width", "height", "slides", "imageFileName",
        "videoFileName", "repeatX", "repeatY", "infiniteLoop",
        "videoOpacity"
})
public class Presentation extends ModelObject {

    public enum Layout {
        FIXED_SIZE,
        FIXED_WIDTH,
        FIXED_HEIGHT,
        FILL
    }

    public Presentation() {
    }

    // stylesheet support

    private final ObservableList<String> stylesheets = FXCollections.observableArrayList();

    public final ObservableList<String> getStylesheets() {
        return stylesheets;
    }

    // layout support

    private final ObjectProperty<Layout> layout = new SimpleObjectProperty<>(this, "layout", Layout.FILL);

    public final ObjectProperty<Layout> layoutProperty() {
        return layout;
    }

    public final void setLayout(Layout layout) {
        this.layout.set(layout);
    }

    public final Layout getLayout() {
        return layout.get();
    }

    // width support

    private final DoubleProperty width = new SimpleDoubleProperty(this, "width", 800);

    public final DoubleProperty widthProperty() {
        return width;
    }

    public final void setWidth(double width) {
        this.width.set(width);
    }

    public final double getWidth() {
        return width.get();
    }

    // height support

    private final DoubleProperty height = new SimpleDoubleProperty(this, "height", 600);

    public final DoubleProperty heightProperty() {
        return height;
    }

    public final void setHeight(double height) {
        this.height.set(height);
    }

    public final double getHeight() {
        return height.get();
    }

    // slides support

    private final ObservableList<Slide> slides = FXCollections.observableArrayList();

    @XmlElement(name = "slide")
    public final ObservableList<Slide> getSlides() {
        return slides;
    }

    // background image support

    private final StringProperty imageFileName = new SimpleStringProperty(this, "imageFileName");

    public final StringProperty imageFileNameProperty() {
        return imageFileName;
    }

    public final String getImageFileName() {
        return imageFileName.get();
    }

    public final void setImageFileName(String fileName) {
        this.imageFileName.set(fileName);
    }

    // background video support

    private final StringProperty videoFileName = new SimpleStringProperty(this, "videoFileName");

    public final StringProperty videoFileNameProperty() {
        return videoFileName;
    }

    public final String getVideoFileName() {
        return videoFileName.get();
    }

    public final void setVideoFileName(String fileName) {
        this.videoFileName.set(fileName);
    }

    // repeat x support

    private ObjectProperty<BackgroundRepeat> repeatX = new SimpleObjectProperty<>(this, "repeat-x", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatXProperty() {
        return repeatX;
    }

    public final BackgroundRepeat getRepeatX() {
        return repeatX.get();
    }

    public void setRepeatX(BackgroundRepeat repeat) {
        this.repeatX.set(repeat);
    }

    // repeat y support

    private ObjectProperty<BackgroundRepeat> repeatY = new SimpleObjectProperty<>(this, "repeat-y", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatYProperty() {
        return repeatY;
    }

    public final BackgroundRepeat getRepeatY() {
        return repeatY.get();
    }

    public void setRepeatY(BackgroundRepeat repeat) {
        this.repeatY.set(repeat);
    }

    // video looping

    private final BooleanProperty infiniteLoop = new SimpleBooleanProperty(this, "infiniteLoop");

    public final BooleanProperty infiniteLoopProperty() {
        return infiniteLoop;
    }

    public final void setInfiniteLoop(boolean infiniteLoop) {
        this.infiniteLoop.set(infiniteLoop);
    }

    public final boolean getInfiniteLoop() {
        return infiniteLoop.get();
    }

    // video opacity

    private final DoubleProperty videoOpacity = new SimpleDoubleProperty(this, "videoOpacity", 1);

    public final DoubleProperty videoOpacityProperty() {
        return videoOpacity;
    }

    public final void setVideoOpacity(double videoOpacity) {
        this.videoOpacity.set(videoOpacity);
    }

    public final double getVideoOpacity() {
        return videoOpacity.get();
    }
}
