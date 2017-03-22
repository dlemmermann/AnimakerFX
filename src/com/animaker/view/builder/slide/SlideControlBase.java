package com.animaker.view.builder.slide;

import com.animaker.model.Slide;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

public abstract class SlideControlBase extends StackPane {

    private Workbench workbench;

    protected SlideControlBase(Workbench workbench) {
        this.workbench = workbench;

        slideProperty().bind(workbench.selectedSlideProperty());

        slideProperty().addListener((observable, oldSlide, newSlide) -> updateSlide(oldSlide, newSlide));
    }

    protected abstract void updateSlide(Slide oldSlide, Slide newSlide);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return getWorkbench().getProject();
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
