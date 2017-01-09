package com.animaker.view.builder;

import com.animaker.model.Slide;
import com.animaker.model.Slider;
import com.animaker.view.skins.builder.SlidesPaletteViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class SlidesPaletteView extends Control {

    public SlidesPaletteView() {
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SlidesPaletteViewSkin(this);
    }

    // slider support

    private final ObjectProperty<Slider> slider = new SimpleObjectProperty<>(this, "slider");

    public final ObjectProperty<Slider> sliderProperty() {
        return slider;
    }

    public final Slider getSlider() {
        return slider.get();
    }

    public final void setSlider(Slider slider) {
        this.slider.set(slider);
    }

    // selected slide support

    private final ObjectProperty<Slide> selectedSlide = new SimpleObjectProperty<>(this, "selectedSlide");

    public final ObjectProperty<Slide> selectedSlideProperty() {
        return selectedSlide;
    }

    public final Slide getSelectedSlide() {
        return selectedSlide.get();
    }

    public final void setSelectedSlide(Slide selectedSlide) {
        this.selectedSlide.set(selectedSlide);
    }
}
