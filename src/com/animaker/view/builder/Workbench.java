package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.model.Presentation;
import com.animaker.model.Slide;
import com.animaker.view.skins.builder.WorkbenchSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 19.12.16.
 */
public class Workbench extends Control {


    public Workbench() {
        getStylesheets().add(Workbench.class.getResource("styles.css").toExternalForm());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new WorkbenchSkin(this);
    }

    // project support

    private final ObjectProperty<Project> project = new SimpleObjectProperty<>(this, "project");

    public final ObjectProperty<Project> projectProperty() {
        return project;
    }

    public final void setProject(Project project) {
        this.project.set(project);
    }

    public final Project getProject() {
        return project.get();
    }

    // presentation support

    private final ObjectProperty<Presentation> presentation = new SimpleObjectProperty<>(this, "presentation");

    public final ObjectProperty<Presentation> presentationProperty() {
        return presentation;
    }

    public final void setPresentation(Presentation presentation) {
        this.presentation.set(presentation);
    }

    public final Presentation getPresentation() {
        return presentation.get();
    }

    // selected slide support

    private final ObjectProperty<Slide> selectedSlide = new SimpleObjectProperty<>(this, "selectedSlide");

    public final ObjectProperty<Slide> selectedSlideProperty() {
        return selectedSlide;
    }

    public final void setSelectedSlide(Slide slide) {
        this.selectedSlide.set(slide);
    }

    public final Slide getSelectedSlide() {
        return selectedSlide.get();
    }

    // selected layer support

    private final ObjectProperty<Layer> selectedLayer = new SimpleObjectProperty<>(this, "selectedLayer");

    public final ObjectProperty<Layer> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Layer layer) {
        this.selectedLayer.set(layer);
    }

    public final Layer getSelectedLayer() {
        return selectedLayer.get();
    }
}
