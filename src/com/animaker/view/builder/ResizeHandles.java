package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.view.LayerView;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Created by lemmi on 13.03.17.
 */
public class ResizeHandles extends StackPane {

    private final LayerView layerView;

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

    public ResizeHandles(LayerView layerView) {
        this.layerView = layerView;

        Layer layer = layerView.getLayer();

        layoutXProperty().bind(layer.layoutXProperty());
        layoutYProperty().bind(layer.layoutYProperty());
        prefWidthProperty().bind(layer.widthProperty());
        prefHeightProperty().bind(layer.heightProperty());

        for (ResizeMode mode : ResizeMode.values()) {
            ResizeHandle handle = new ResizeHandle(mode);
            getChildren().add(handle);
            StackPane.setAlignment(handle, mode.getPos());
        }
    }

    public LayerView getLayerView() {
        return layerView;
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

                final Layer layer = layerView.getLayer();

                System.out.println(mode);

                switch (mode) {
                    case RESIZE_NW:
                        layer.setLayoutX(layer.getLayoutX() + deltaX);
                        layer.setLayoutY(layer.getLayoutY() + deltaY);
                        layer.setWidth(layer.getWidth() - deltaX);
                        layer.setHeight(layer.getHeight() - deltaY);
                        break;
                    case RESIZE_NE:
                        layer.setLayoutY(layer.getLayoutY() + deltaY);
                        layer.setWidth(layer.getWidth() + deltaX);
                        layer.setHeight(layer.getHeight() - deltaY);
                        break;
                    case RESIZE_SE:
                        layer.setWidth(layer.getWidth() + deltaX);
                        layer.setHeight(layer.getHeight() + deltaY);
                        break;
                    case RESIZE_SW:
                        layer.setLayoutX(layer.getLayoutX() + deltaX);
                        layer.setWidth(layer.getWidth() - deltaX);
                        layer.setHeight(layer.getHeight() + deltaY);
                        break;
                    case RESIZE_N:
                        layer.setLayoutY(layer.getLayoutY() + deltaY);
                        layer.setHeight(layer.getHeight() - deltaY);
                        break;
                    case RESIZE_W:
                        layer.setLayoutX(layer.getLayoutX() + deltaX);
                        layer.setWidth(layer.getWidth() - deltaX);
                        break;
                    case RESIZE_E:
                        layer.setWidth(layer.getWidth() + deltaX);
                        break;
                    case RESIZE_S:
                        layer.setHeight(layer.getHeight() + deltaY);
                        break;
                }

                layerView.getPresentationView().requestLayout();
            });
        }
    }
}
