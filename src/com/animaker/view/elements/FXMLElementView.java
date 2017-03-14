package com.animaker.view.elements;

import com.animaker.model.elements.FXMLElement;
import com.animaker.model.elements.HTMLElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by lemmi on 14.03.17.
 */
public class FXMLElementView extends ElementView<FXMLElement> {

    public FXMLElementView(SlideView slideView, FXMLElement element) {
        super(slideView, element);
    }
}
