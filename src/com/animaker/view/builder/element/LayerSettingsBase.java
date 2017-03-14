package com.animaker.view.builder.element;

import com.animaker.model.Element;
import com.animaker.model.Project;
import com.animaker.view.builder.Workbench;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public abstract class LayerSettingsBase extends StackPane {

    private Workbench workbench;

    protected LayerSettingsBase(Workbench workbench) {
        this.workbench = workbench;

        layerProperty().bindBidirectional(workbench.selectedElementProperty());

        layerProperty().addListener((observable, oldLayer, newLayer) -> updateView(oldLayer, newLayer));

        Label layerName = new Label();
        layerProperty().addListener(it -> {
            Element element = getLayer();
            if (element != null) {
                layerName.setText(element.getName());
            } else {
                layerName.setText("No element selected...");
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layerName);
        borderPane.setCenter(createContent());

        getChildren().add(borderPane);
    }

    protected Node createContent() {
        return new Label("Missing content. Not implemented, yet.");
    }

    protected abstract void updateView(Element oldElement, Element newElement);

    public final Workbench getWorkbench() {
        return workbench;
    }

    public final Project getProject() {
        return getWorkbench().getProject();
    }

    private final ObjectProperty<Element> layer = new SimpleObjectProperty<>(this, "element");

    public final ObjectProperty<Element> layerProperty() {
        return layer;
    }

    public final Element getLayer() {
        return layer.get();
    }

    public final void setLayer(Element element) {
        this.layer.set(element);
    }
}
