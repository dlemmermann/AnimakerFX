package com.animaker.view.builder;

import com.animaker.model.Element;
import com.animaker.view.ElementView;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 13.03.17.
 */
public class ResizeHandles extends StackPane {

    private final ElementView elementView;

    private enum ResizeMode {
        RESIZE_W(Pos.CENTER_LEFT),
        RESIZE_E(Pos.CENTER_RIGHT),
        RESIZE_N(Pos.TOP_CENTER),
        RESIZE_S(Pos.BOTTOM_CENTER),
        RESIZE_NW(Pos.TOP_LEFT),
        RESIZE_SW(Pos.BOTTOM_LEFT),
        RESIZE_NE(Pos.TOP_RIGHT),
        RESIZE_SE(Pos.BOTTOM_RIGHT);

        private Pos pos;

        ResizeMode(Pos pos) {
            this.pos = pos;
        }

        public Pos getPos() {
            return pos;
        }
    }

    private Workbench workbench;

    public ResizeHandles(Workbench workbench, ElementView elementView) {
        this.elementView = elementView;
        this.workbench = workbench;

        Element element = elementView.getElement();

        layoutXProperty().bind(element.layoutXProperty());
        layoutYProperty().bind(element.layoutYProperty());
        prefWidthProperty().bind(element.widthProperty());
        prefHeightProperty().bind(element.heightProperty());

        for (ResizeMode mode : ResizeMode.values()) {
            ResizeHandle handle = new ResizeHandle(mode);
            getChildren().add(handle);
            StackPane.setAlignment(handle, mode.getPos());
        }

        elementView.sceneProperty().addListener(it -> {
            if (elementView.getScene() == null) {
                workbench.stopResize(elementView);
            }
        });
    }

    public ElementView getElementView() {
        return elementView;
    }

    public class ResizeHandle extends Region {

        private double mouseX;
        private double mouseY;

        public ResizeHandle(ResizeMode mode) {
            getStyleClass().addAll("resize-handle", mode.getPos().toString().toLowerCase().replace('_', '-'));

            setOnMousePressed(evt -> {
                mouseX = evt.getSceneX();
                mouseY = evt.getSceneY();
            });

            setOnMouseDragged(evt -> {
                double deltaX = evt.getSceneX() - mouseX;
                double deltaY = evt.getSceneY() - mouseY;

                mouseX = evt.getSceneX();
                mouseY = evt.getSceneY();

                final Element element = elementView.getElement();

                System.out.println(mode);

                switch (mode) {
                    case RESIZE_NW:
                        element.setLayoutX(element.getLayoutX() + deltaX);
                        element.setLayoutY(element.getLayoutY() + deltaY);
                        element.setWidth(element.getWidth() - deltaX);
                        element.setHeight(element.getHeight() - deltaY);
                        break;
                    case RESIZE_NE:
                        element.setLayoutY(element.getLayoutY() + deltaY);
                        element.setWidth(element.getWidth() + deltaX);
                        element.setHeight(element.getHeight() - deltaY);
                        break;
                    case RESIZE_SE:
                        element.setWidth(element.getWidth() + deltaX);
                        element.setHeight(element.getHeight() + deltaY);
                        break;
                    case RESIZE_SW:
                        element.setLayoutX(element.getLayoutX() + deltaX);
                        element.setWidth(element.getWidth() - deltaX);
                        element.setHeight(element.getHeight() + deltaY);
                        break;
                    case RESIZE_N:
                        element.setLayoutY(element.getLayoutY() + deltaY);
                        element.setHeight(element.getHeight() - deltaY);
                        break;
                    case RESIZE_W:
                        element.setLayoutX(element.getLayoutX() + deltaX);
                        element.setWidth(element.getWidth() - deltaX);
                        break;
                    case RESIZE_E:
                        element.setWidth(element.getWidth() + deltaX);
                        break;
                    case RESIZE_S:
                        element.setHeight(element.getHeight() + deltaY);
                        break;
                }

                elementView.getPresentationView().requestLayout();
            });
        }
    }
}
