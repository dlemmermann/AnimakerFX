package com.animaker.model.elements;

import com.animaker.model.Element;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BackgroundRepeat;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 14.03.17.
 */
@XmlType(name = "region")
public class RegionElement extends Element {

    public RegionElement() {
    }

    @Override
    public ElementType getType() {
        return ElementType.REGION;
    }

    private final StringProperty backgroundImageFileName = new SimpleStringProperty(this, "backgroundImageFileName");

    public final StringProperty backgroundImageFileNameProperty() {
        return backgroundImageFileName;
    }

    public final String getBackgroundImageFileName() {
        return backgroundImageFileName.get();
    }

    public final void setBackgroundImageFileName(String content) {
        this.backgroundImageFileName.set(content);
    }

    // repeat x support

    private ObjectProperty<BackgroundRepeat> repeatX = new SimpleObjectProperty<>(this, "repeat-x", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatXProperty() {
        return repeatX;
    }

    public final BackgroundRepeat getRepeatX() {
        return repeatX.get();
    }

    public void setRepeatX(BackgroundRepeat repeat) {
        this.repeatX.set(repeat);
    }

    // repeat y support

    private ObjectProperty<BackgroundRepeat> repeatY = new SimpleObjectProperty<>(this, "repeat-y", BackgroundRepeat.NO_REPEAT);

    public final ObjectProperty<BackgroundRepeat> repeatYProperty() {
        return repeatY;
    }

    public final BackgroundRepeat getRepeatY() {
        return repeatY.get();
    }

    public void setRepeatY(BackgroundRepeat repeat) {
        this.repeatY.set(repeat);
    }
}