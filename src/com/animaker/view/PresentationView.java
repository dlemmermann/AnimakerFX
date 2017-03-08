package com.animaker.view;

import com.animaker.model.Presentation;
import com.animaker.model.Slide;
import com.animaker.view.builder.Project;
import com.animaker.view.skins.PresentationViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.util.Objects;

/**
 * Created by lemmi on 19.12.16.
 */
public class PresentationView extends Control {

    private final Project project;
    private final Presentation presentation;

    public PresentationView(Project project, Presentation presentation) {
        this.project = Objects.requireNonNull(project);
        this.presentation = Objects.requireNonNull(presentation);

        getStyleClass().add("presentation");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PresentationViewSkin(this);
    }

    public final Project getProject() {
        return project;
    }

    public final Presentation getPresentation() {
        return presentation;
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

    public enum Status {
        STOPPED,
        PAUSED,
        PLAY,
    }

    // status support

    private final ObjectProperty<Status> status = new SimpleObjectProperty<>(this, "status", Status.STOPPED);

    public final ObjectProperty<Status> statusProperty() {
        return status;
    }

    public final void setStatus(Status status) {
        this.status.set(status);
    }

    public final Status getStatus() {
        return status.get();
    }
}
