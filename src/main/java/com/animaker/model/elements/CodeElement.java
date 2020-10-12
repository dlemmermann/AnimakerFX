package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "code")
public class CodeElement extends Element {

    public CodeElement() {
    }

    @Override
    public ElementType getType() {
        return ElementType.CODE;
    }

    // code content support

    private final StringProperty className = new SimpleStringProperty(this, "className", "com.animaker.view.builder.TestStatusBar");

    public final StringProperty classNameProperty() {
        return className;
    }

    public final String getClassName() {
        return className.get();
    }

    public final void setClassName(String content) {
        this.className.set(content);
    }
}
