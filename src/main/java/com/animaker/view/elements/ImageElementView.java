package com.animaker.view.elements;

import java.io.File;

import com.animaker.model.elements.ImageElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by lemmi on 14.03.17.
 */
public class ImageElementView extends ElementView<ImageElement> {

    public ImageElementView(SlideView slideView, ImageElement element) {
        super(slideView, element);

        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);
        imageView.fitWidthProperty().bind(element.widthProperty());
        imageView.fitHeightProperty().bind(element.heightProperty());
        imageView.setPreserveRatio(false);

        updateImageView(imageView);
        getElement().imageFileNameProperty().addListener(it -> updateImageView(imageView));

        getChildren().add(imageView);
    }

    private void updateImageView(ImageView imageView) {
        try {
            ImageElement element = getElement();
            final String imageFileName = element.getImageFileName();
            if (imageFileName != null) {
                File file = getPresentationView().getProject().getFile(imageFileName);
                Image image = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(image);
            } else {
                imageView.setImage(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
