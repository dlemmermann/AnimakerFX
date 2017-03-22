package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.view.builder.Workbench;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by lemmi on 20.12.16.
 */
public class TransitionSettingsView extends ElementSettingsView<Element> {

    public TransitionSettingsView(Workbench workbench, String title) {
        super(workbench);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold;");
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        HBox header = new HBox(10);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(header);
        BorderPane.setMargin(header, new Insets(10));

        getChildren().add(borderPane);

        elementProperty().bind(workbench.selectedElementProperty());
    }

    @Override
    protected void update(Element oldElement, Element newElement) {
    }
}
