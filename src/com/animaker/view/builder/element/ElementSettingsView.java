package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 14.03.17.
 */
public abstract class ElementSettingsView<T extends Element> extends StackPane {

    private Workbench workbench;

    protected ElementSettingsView(Workbench workbench) {
        this.workbench = workbench;

        elementProperty().addListener((observable, oldElement, newElement) -> update(oldElement, newElement));

        setStyle("-fx-background-color: -fx-base;");
    }

    protected abstract void update(T oldElement, T newElement);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return workbench.getProject();
    }

    // element support

    private final ObjectProperty<T> element = new SimpleObjectProperty<>(this, "element");

    public final ObjectProperty<T> elementProperty() {
        return element;
    }

    public final T getElement() {
        return element.get();
    }

    public final void setElement(T element) {
        this.element.set(element);
    }
}
