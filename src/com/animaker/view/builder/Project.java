package com.animaker.view.builder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by lemmi on 07.03.17.
 */
public class Project {

    public Project() {
    }

    public Project(String name, String location) {
        setName(name);
        setLocation(location);
    }

    // utility methods

    public File getFile(String fileName) {
        return new File(getLocation(), fileName);
    }

    public Image getImage(String fileName) {
        try {
            File file = new File(getLocation(), fileName);
            Image image = new Image(file.toURI().toURL().toExternalForm());
            return image;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // name support

    private final StringProperty name = new SimpleStringProperty(this, "name");

    public final StringProperty nameProperty() {
        return name;
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    // location support

    private final StringProperty location = new SimpleStringProperty(this, "location");

    public final StringProperty locationProperty() {
        return location;
    }

    public final String getLocation() {
        return location.get();
    }

    public final void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name=" + name.get() +
                ", location=" + location.get() +
                '}';
    }
}
