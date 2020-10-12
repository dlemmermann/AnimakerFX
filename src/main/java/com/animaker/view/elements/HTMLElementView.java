package com.animaker.view.elements;

import com.animaker.model.elements.HTMLElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by lemmi on 14.03.17.
 */
public class HTMLElementView extends ElementView<HTMLElement> {

    public HTMLElementView(SlideView slideView, HTMLElement element) {
        super(slideView, element);

        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.setUserStyleSheetLocation(ElementView.class.getResource("style.css").toExternalForm());
        engine.load(element.getHtmlContent());
        view.prefWidthProperty().bind(widthProperty());
        view.prefHeightProperty().bind(heightProperty());
    }
}
