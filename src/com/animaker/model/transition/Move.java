package com.animaker.model.transition;

import com.animaker.view.ElementView;

/**
 * Created by lemmi on 23.03.17.
 */
public abstract class Move extends Transition {

    public enum TransitionDirection {

        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        TOP_LEFT_TO_BOTTOM_RIGHT,
        TOP_RIGHT_TO_BOTTOM_LEFT,
        BOTTOM_LEFT_TO_TOP_RIGHT,
        BOTTOM_RIGHT_TO_TOP_LEFT

    }

    protected Move() {
        super();
    }

    protected Move(String name) {
        super(name);
    }
}
