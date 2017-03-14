package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "html")
public class HTMLElement extends Element {

    public HTMLElement() {
    }

    @Override
    public ElementType getType() {
        return ElementType.HTML;
    }

    // web content support

    private final StringProperty htmlContent = new SimpleStringProperty(this, "htmlContent");

    public final StringProperty htmlContentProperty() {
        return htmlContent;
    }

    public final String getHtmlContent() {
        return htmlContent.get();
    }

    public final void setHtmlContent(String content) {
        this.htmlContent.set(content);
    }
}
