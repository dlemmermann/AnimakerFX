package com.animaker.view;

import com.animaker.model.Slide;
import com.animaker.model.Slider;
import com.animaker.view.skins.SliderViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.Objects;

/**
 * Created by lemmi on 19.12.16.
 */
public class SliderView extends Control {

    private final Slider slider;

    public SliderView(Slider slider) {
        this.slider = Objects.requireNonNull(slider);

        getStyleClass().add("slider");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SliderViewSkin(this);
    }

    public final Slider getSlider() {
        return slider;
    }

    // current slide support

    private final ObjectProperty<Slide> currentSlide = new SimpleObjectProperty<>(this, "currentSlide");

    public final ObjectProperty<Slide> currentSlideProperty() {
        return currentSlide;
    }

    public final void setCurrentSlide(Slide slide) {
        this.currentSlide.set(slide);
    }

    public final Slide getCurrentSlide() {
        return currentSlide.get();
    }
}
