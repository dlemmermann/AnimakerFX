package com.animaker.model.transition;

import com.animaker.view.LayerView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by lemmi on 19.12.16.
 */
public class FadeIn extends Transition {

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

    public FadeIn() {
        super("FadeIn");
    }

    // text delivery support

    private final ObjectProperty<TextDelivery> textDelivery = new SimpleObjectProperty<>(this, "textDelivery", TextDelivery.BY_OBJECT);

    public final ObjectProperty<TextDelivery> textDeliveryProperty() {
        return textDelivery;
    }

    public final void setTextDelivery(TextDelivery textDelivery) {
        this.textDelivery.set(textDelivery);
    }

    public final TextDelivery getTextDelivery() {
        return textDelivery.get();
    }

    // text direction support

    private final ObjectProperty<TextDirection> textDirection = new SimpleObjectProperty<>(this, "textDirection", TextDirection.FORWARD);

    public final ObjectProperty<TextDirection> textDirectionProperty() {
        return textDirection;
    }

    public final void setTextDirection(TextDirection direction) {
        this.textDirection.set(direction);
    }

    public final TextDirection getTextDirection() {
        return textDirection.get();
    }

    @Override
    public void setup(LayerView layerView) {
        layerView.setOpacity(0);
    }

    @Override
    public void configure(LayerView layerView, Timeline timeline) {
        /*
         * Interpolate from the current opacity used by the layer view to the
         * target opacity defined my the layer model.
         */
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(layerView.opacityProperty(), layerView.getOpacity(), getInterpolator())),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(layerView.opacityProperty(), layerView.getLayer().getOpacity(), getInterpolator())));
    }
}
