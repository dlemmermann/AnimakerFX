package com.animaker.view.builder.element;

import com.animaker.model.elements.TextElement;
import com.animaker.view.builder.Workbench;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Created by lemmi on 14.03.17.
 */
public class TextSettingsView extends ElementSettingsView<TextElement> {

    private final TextArea textArea;

    public TextSettingsView(Workbench workbench) {
        super(workbench);

        textArea = new TextArea();
        textArea.setPrefColumnCount(0);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        BorderPane.setMargin(textArea, new Insets(5, 10, 10, 10));
    }

    @Override
    protected void update(TextElement oldElement, TextElement newElement) {
        textArea.textProperty().bindBidirectional(newElement.textProperty());
    }
}
