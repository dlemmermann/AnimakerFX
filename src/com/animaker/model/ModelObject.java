package com.animaker.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name"})
public abstract class ModelObject {

    public ModelObject() {
    }

    public ModelObject(String name) {
        setName(name);
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
