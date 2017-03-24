package com.animaker.model.transition;

import com.animaker.view.ElementView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 19.12.16.
 */
@XmlType(name = "fade-in")
public class FadeIn extends Fade {

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
    public void setup(ElementView elementView) {
        elementView.setOpacity(0);
    }

    @Override
    public void configure(ElementView elementView, Timeline timeline) {
        /*
         * Interpolate from the current opacity used by the element view to the
         * target opacity defined my the element model.
         */
        timeline.getKeyFrames().addAll(
                new KeyFrame(getDelay(),
                        new KeyValue(elementView.opacityProperty(), elementView.getOpacity(), getInterpolator())),
                new KeyFrame(getDuration().add(getDelay()),
                        new KeyValue(elementView.opacityProperty(), elementView.getElement().getOpacity(), getInterpolator())));
    }
}
