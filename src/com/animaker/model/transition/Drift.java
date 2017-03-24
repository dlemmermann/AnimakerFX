package com.animaker.model.transition;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 19.12.16.
 */
@XmlType(name = "drift")
public class Drift extends Transition {

    public enum DriftDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public Drift() {
        super("Drift");
    }

    // drift direction support

    private final ObjectProperty<DriftDirection> direction = new SimpleObjectProperty(this, "direction", DriftDirection.LEFT_TO_RIGHT);

    public final ObjectProperty<DriftDirection> directionProperty() {
        return direction;
    }

    public final void setDirection(DriftDirection direction) {
        this.direction.set(direction);
    }

    public final DriftDirection getDirection() {
        return direction.get();
    }
}
