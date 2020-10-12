package com.animaker.model.transition;

/**
 * Created by lemmi on 23.03.17.
 */
public abstract class Fade extends Transition {

    public enum TextDelivery {
        BY_WORD,
        BY_OBJECT,
        BY_CHARACTER
    }

    public enum TextDirection {
        FORWARD,
        BACKWARD,
        FROM_CENTER,
        FROM_EDGES,
        RANDOM
    }

    protected Fade() {
        super();
    }

    protected Fade(String name) {
        super(name);
    }
}
