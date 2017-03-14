package com.animaker.view.elements;

import com.animaker.model.elements.TextElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.beans.binding.Bindings;
import javafx.scene.text.Text;

/**
 * Created by lemmi on 14.03.17.
 */
public class TextElementView extends ElementView<TextElement> {

    public TextElementView(SlideView slideView, TextElement element) {
        super(slideView, element);

        Text text = new Text();
        text.textProperty().bind(element.textProperty());
        text.styleProperty().bind(element.styleProperty());
        Bindings.bindContent(text.getStyleClass(), element.getStyleClass());
        getChildren().add(text);
    }
}
