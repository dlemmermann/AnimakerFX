package com.animaker.model;

import com.animaker.util.ImageAdapter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

@XmlRootElement(name="presentation")
@XmlType(propOrder = {"layout", "width", "height", "slides", "backgroundImage"})
public class Presentation extends ModelObject {

    public enum SliderLayout {
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

    private final ObjectProperty<SliderLayout> layout = new SimpleObjectProperty<>(this, "layout", SliderLayout.FILL);

    public final ObjectProperty<SliderLayout> layoutProperty() {
        return layout;
    }

    public final void setLayout(SliderLayout layout) {
        this.layout.set(layout);
    }

    public final SliderLayout getLayout() {
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

    // image content support
    // TODO: more background styling? repeats? More like CSS?

    private final ObjectProperty<Image> backgroundImage = new SimpleObjectProperty<>(this, "backgroundImage");

    public final ObjectProperty backgroundImageProperty() {
        return backgroundImage;
    }

    @XmlJavaTypeAdapter(value=ImageAdapter.class)
    public final Image getBackgroundImage() {
        return backgroundImage.get();
    }

    public final void setBackgroundImage(Image content) {
        this.backgroundImage.set(content);
    }
}
