package com.animaker.view.elements;

import com.animaker.model.elements.RegionElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.beans.InvalidationListener;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

import java.io.File;

/**
 * Created by lemmi on 14.03.17.
 */
public class RegionElementView extends ElementView<RegionElement> {

    private final InvalidationListener updateRegionListener = it -> updateRegionBackground();

    public RegionElementView(SlideView slideView, RegionElement element) {
        super(slideView, element);

        element.backgroundImageFileNameProperty().addListener(updateRegionListener);
        element.repeatXProperty().addListener(updateRegionListener);
        element.repeatYProperty().addListener(updateRegionListener);

        updateRegionBackground();
    }

    private void updateRegionBackground() {
        try {
            RegionElement element = getElement();
            final String imageFileName = element.getBackgroundImageFileName();
            if (imageFileName != null) {
                File file = getPresentationView().getProject().getFile(imageFileName);
                Image image = new Image(file.toURI().toURL().toExternalForm());
                BackgroundImage backgroundImage = new BackgroundImage(image, element.getRepeatX(), element.getRepeatY(), BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true));
                setBackground(new Background(backgroundImage));
            } else {
                setBackground(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
