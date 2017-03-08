package com.animaker.model;

import com.animaker.model.transition.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Slide")
public class Slide extends ModelObject {


    public Slide() {
    }

    public Slide(String name) {
        super(name);
    }

    // image content support

    private final ObjectProperty<Image> backgroundImage = new SimpleObjectProperty<>(this, "backgroundImage");

    public final ObjectProperty backgroundImageProperty() {
        return backgroundImage;
    }

    public final Image getBackgroundImage() {
        return backgroundImage.get();
    }

    public final void setBackgroundImage(Image content) {
        this.backgroundImage.set(content);
    }

    // transition support

    private final ObjectProperty<Transition> transition = new SimpleObjectProperty<>(this, "transition");

    public final ObjectProperty<Transition> transitionProperty() {
        return transition;
    }

    public final void setTransition(Transition transition) {
        this.transition.set(transition);
    }

    public final Transition getTransition() {
        return transition.get();
    }

    // layers support

    private ObservableList<Layer> layers = FXCollections.observableArrayList();

    @XmlElement(name = "layer")
    public final ObservableList<Layer> getLayers() {
        return layers;
    }
}
