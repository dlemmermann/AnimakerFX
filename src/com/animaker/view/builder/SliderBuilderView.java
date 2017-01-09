package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.model.Slide;
import com.animaker.model.Slider;
import com.animaker.view.skins.builder.SliderBuilderViewSkin;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * Created by lemmi on 19.12.16.
 */
public class SliderBuilderView extends Control {

    public SliderBuilderView() {
        getStylesheets().add(SliderBuilderView.class.getResource("styles.css").toExternalForm());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new SliderBuilderViewSkin(this);
    }

    private final ObjectProperty<Slider> slider = new SimpleObjectProperty<>(this, "slider");

    public final ObjectProperty<Slider> sliderProperty() {
        return slider;
    }

    public final void setSlider(Slider slider) {
        this.slider.set(slider);
    }

    public final Slider getSlider() {
        return slider.get();
    }

    // selected slide support

    private final ObjectProperty<Slide> selectedSlide = new SimpleObjectProperty<>(this, "selectedSlide");

    public final ObjectProperty<Slide> selectedSlideProperty() {
        return selectedSlide;
    }

    public final void setSelectedSlide(Slide slide) {
        this.selectedSlide.set(slide);
    }

    public final Slide getSelectedSlide() {
        return selectedSlide.get();
    }

    // selected layer support

    private final ObjectProperty<Layer> selectedLayer = new SimpleObjectProperty<>(this, "selectedLayer");

    public final ObjectProperty<Layer> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Layer layer) {
        this.selectedLayer.set(layer);
    }

    public final Layer getSelectedLayer() {
        return selectedLayer.get();
    }
}
