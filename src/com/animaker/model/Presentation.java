package com.animaker.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
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

@XmlRootElement(name="presentation")
@XmlType(propOrder = {"layout", "width", "height", "slides", "backgroundImageFileName", "backgroundVideoFileName", "backgroundRepeat"})
public class Presentation extends ModelObject {

    public enum Layout {
        FIXED_SIZE,
        FIXED_WIDTH,
        FIXED_HEIGHT,
        FILL
    }

    public Presentation() {
    }

    public Presentation(String name) {
        super(name);
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

    private final StringProperty backgroundImageFileName = new SimpleStringProperty(this, "backgroundImageFileName");

    public final StringProperty backgroundImageFileNameProperty() {
        return backgroundImageFileName;
    }

    public final String getBackgroundImageFileName() {
        return backgroundImageFileName.get();
    }

    public final void setBackgroundImageFileName(String fileName) {
        this.backgroundImageFileName.set(fileName);
    }

    // background video support

    private final StringProperty backgroundVideoFileName = new SimpleStringProperty(this, "backgroundVideoFileName");

    public final StringProperty backgroundVideoFileNameProperty() {
        return backgroundVideoFileName;
    }

    public final String getBackgroundVideoFileName() {
        return backgroundVideoFileName.get();
    }

    public final void setBackgroundVideoFileName(String fileName) {
        this.backgroundVideoFileName.set(fileName);
    }

    // background repeat

    private final ObjectProperty<BackgroundRepeat> backgroundRepeat = new SimpleObjectProperty<>(this, "backgroundRepeat", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> backgroundRepeatProperty() {
        return backgroundRepeat;
    }

    public final void setBackgroundRepeat(BackgroundRepeat backgroundRepeat) {
        this.backgroundRepeat.set(backgroundRepeat);
    }

    public final BackgroundRepeat getBackgroundRepeat() {
        return backgroundRepeat.get();
    }
}
