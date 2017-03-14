package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "image")
public class ImageElement extends Element {

    public ImageElement() {
        super();
    }

    @Override
    public ElementType getType() {
        return ElementType.IMAGE;
    }

    // image file name support

    private final StringProperty imageFileName = new SimpleStringProperty(this, "imageFileName");

    public final StringProperty imageFileNameProperty() {
        return imageFileName;
    }

    public final String getImageFileName() {
        return imageFileName.get();
    }

    public final void setImageFileName(String content) {
        this.imageFileName.set(content);
    }

    // preserve ratio support

    private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio", false);

    public final BooleanProperty preserveRatioProperty() {
        return preserveRatio;
    }

    public final void setPreserveRatio(boolean preserveRatio) {
        this.preserveRatio.set(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return preserveRatio.get();
    }
}
