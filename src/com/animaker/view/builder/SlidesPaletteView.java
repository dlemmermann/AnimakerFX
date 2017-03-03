package com.animaker.view.builder;

import com.animaker.model.Presentation;
import com.animaker.model.Slide;
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

    // presentation support

    private final ObjectProperty<Presentation> presentation = new SimpleObjectProperty<>(this, "presentation");

    public final ObjectProperty<Presentation> presentationProperty() {
        return presentation;
    }

    public final Presentation getPresentation() {
        return presentation.get();
    }

    public final void setPresentation(Presentation presentation) {
        this.presentation.set(presentation);
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
