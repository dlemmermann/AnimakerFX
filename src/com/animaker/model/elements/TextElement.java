package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "text")
public class TextElement extends Element {

    public TextElement() {
        super();
    }

    @Override
    public ElementType getType() {
        return ElementType.TEXT;
    }

    // text content support

    private final StringProperty text = new SimpleStringProperty(this, "text");

    public final StringProperty textProperty() {
        return text;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(String content) {
        this.text.set(content);
    }
}
