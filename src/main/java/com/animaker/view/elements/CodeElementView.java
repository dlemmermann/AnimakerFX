package com.animaker.view.elements;

import com.animaker.model.elements.CodeElement;
import com.animaker.view.ElementView;
import com.animaker.view.SlideView;

import javafx.scene.Node;

/**
 * Created by lemmi on 14.03.17.
 */
public class CodeElementView extends ElementView<CodeElement> {

    public CodeElementView(SlideView slideView, CodeElement element) {
        super(slideView, element);

        element.classNameProperty().addListener(it -> updateView());
        updateView();
    }

    private void updateView() {
        try {
            CodeElement element = getElement();
            String className = element.getClassName();
            if (className != null) {
                final Class<?> clazz = Class.forName(className);
                final Object o = clazz.newInstance();
                if (o instanceof Node) {
                   getChildren().setAll((Node) o);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
