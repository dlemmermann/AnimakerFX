package com.animaker.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name"})
public abstract class ModelObject {

    public ModelObject() {
    }

    private final ObjectProperty<ModelObject> self = new SimpleObjectProperty<>(this);

    /**
     * Needed for tree item value provider (tree table view support)
     *
     * @return the object itself
     */
    public ObjectProperty<ModelObject> selfProperty() {
        return self;
    }

    // name support

    private final StringProperty name = new SimpleStringProperty(this, "name", "Untitled");

    public final StringProperty nameProperty() {
        return name;
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return getName();
    }
}
