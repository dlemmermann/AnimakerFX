package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "fxml")
public class FXMLElement extends Element {

    public FXMLElement() {
        super();
    }

    @Override
    public ElementType getType() {
        return ElementType.FXML;
    }

    // fxml content support

    private final StringProperty fxmlFileName = new SimpleStringProperty(this, "fxmlFileName");

    public final StringProperty fxmlFileNameProperty() {
        return fxmlFileName;
    }

    public final String getFxmlFileName() {
        return fxmlFileName.get();
    }

    public final void setFxmlFileName(String content) {
        this.fxmlFileName.set(content);
    }

}
