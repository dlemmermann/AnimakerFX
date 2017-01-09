package com.animaker.view.builder;

import com.animaker.model.Slide;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;

public abstract class SlideControlBase extends Control {

    protected SlideControlBase() {

    }

    private final ObjectProperty<Slide> slide = new SimpleObjectProperty<>(this, "slide");

    public final ObjectProperty<Slide> slideProperty() {
        return slide;
    }

    public final Slide getSlide() {
        return slide.get();
    }

    public final void setSlide(Slide slide) {
        this.slide.set(slide);
    }
}
