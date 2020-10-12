/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.animaker.model;

import com.animaker.model.transition.Blur;
import com.animaker.model.transition.Drift;
import com.animaker.model.transition.FadeIn;
import com.animaker.model.transition.FadeOut;
import com.animaker.model.transition.MoveIn;
import com.animaker.model.transition.MoveOut;
import com.animaker.model.transition.Parallax;
import com.animaker.model.transition.Play;
import com.animaker.model.transition.Rotate;
import com.animaker.model.transition.Scale;
import com.animaker.model.transition.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public abstract class Element extends ModelObject {

    public Element() {
    }

    public enum ElementType {

        REGION("Region"),
        IMAGE("Image"),
        TEXT("Text"),
        VIDEO("Video"),
        FXML("FXML"),
        CODE("Code"),
        HTML("HTML");

        private String displayName;

        ElementType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public abstract ElementType getType();

    // style support

    private final StringProperty style = new SimpleStringProperty(this, "style");

    public final StringProperty styleProperty() {
        return style;
    }

    public final String getStyle() {
        return style.get();
    }

    public final void setStyle(String style) {
        this.style.set(style);
    }

    // style class support

    private final ObservableList<String> styleClass = FXCollections.observableArrayList();

    public final ObservableList<String> getStyleClass() {
        return styleClass;
    }

    // opacity

    private final DoubleProperty opacity = new SimpleDoubleProperty(this, "opacity", 1);

    public final DoubleProperty opacityProperty() {
        return opacity;
    }

    public final double getOpacity() {
        return opacity.get();
    }

    public final void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    // rotation

    private final DoubleProperty rotation = new SimpleDoubleProperty(this, "rotation", 0);

    public final DoubleProperty rotationProperty() {
        return rotation;
    }

    public final double getRotation() {
        return rotation.get();
    }

    public final void setRotation(double rotation) {
        this.rotation.set(rotation);
    }

    // scale X

    private final DoubleProperty scaleX = new SimpleDoubleProperty(this, "scaleX", 1);

    public final DoubleProperty scaleXProperty() {
        return scaleX;
    }

    public final double getScaleX() {
        return scaleX.get();
    }

    public final void setScaleX(double scale) {
        this.scaleX.set(scale);
    }

    // scale Y

    private final DoubleProperty scaleY = new SimpleDoubleProperty(this, "scaleY", 1);

    public final DoubleProperty scaleYProperty() {
        return scaleY;
    }

    public final double getScaleY() {
        return scaleY.get();
    }

    public final void setScaleY(double scale) {
        this.scaleY.set(scale);
    }

    // transition support

    private final ObservableList<Transition> openingTransitions = FXCollections.observableArrayList();

    @XmlElementWrapper(name = "opening-transitions")
    @XmlElements({
            @XmlElement(name = "blur", type = Blur.class),
            @XmlElement(name = "drift", type = Drift.class),
            @XmlElement(name = "fade-in", type = FadeIn.class),
            @XmlElement(name = "fade-out", type = FadeOut.class),
            @XmlElement(name = "move-in", type = MoveIn.class),
            @XmlElement(name = "move-out", type = MoveOut.class),
            @XmlElement(name = "parallax", type = Parallax.class),
            @XmlElement(name = "play", type = Play.class),
            @XmlElement(name = "rotate", type = Rotate.class),
            @XmlElement(name = "scale", type = Scale.class)
    })
    public final ObservableList<Transition> getOpeningTransitions() {
        return openingTransitions;
    }

    private final ObservableList<Transition> loopTransitions = FXCollections.observableArrayList();

    @XmlElementWrapper(name = "loop-transitions")
    @XmlElements({
            @XmlElement(name = "blur", type = Blur.class),
            @XmlElement(name = "drift", type = Drift.class),
            @XmlElement(name = "fade-in", type = FadeIn.class),
            @XmlElement(name = "fade-out", type = FadeOut.class),
            @XmlElement(name = "move-in", type = MoveIn.class),
            @XmlElement(name = "move-out", type = MoveOut.class),
            @XmlElement(name = "parallax", type = Parallax.class),
            @XmlElement(name = "play", type = Play.class),
            @XmlElement(name = "rotate", type = Rotate.class),
            @XmlElement(name = "scale", type = Scale.class)
    })
    public final ObservableList<Transition> getLoopTransitions() {
        return loopTransitions;
    }

    private final ObservableList<Transition> closingTransitions = FXCollections.observableArrayList();

    @XmlElementWrapper(name = "closing-transitions")
    @XmlElements({
            @XmlElement(name = "blur", type = Blur.class),
            @XmlElement(name = "drift", type = Drift.class),
            @XmlElement(name = "fade-in", type = FadeIn.class),
            @XmlElement(name = "fade-out", type = FadeOut.class),
            @XmlElement(name = "move-in", type = MoveIn.class),
            @XmlElement(name = "move-out", type = MoveOut.class),
            @XmlElement(name = "parallax", type = Parallax.class),
            @XmlElement(name = "play", type = Play.class),
            @XmlElement(name = "rotate", type = Rotate.class),
            @XmlElement(name = "scale", type = Scale.class)
    })
    public final ObservableList<Transition> getClosingTransitions() {
        return closingTransitions;
    }

    private final ObservableList<Transition> hoverTransitions = FXCollections.observableArrayList();

    @XmlElementWrapper(name = "hover-transitions")
    @XmlElements({
            @XmlElement(name = "blur", type = Blur.class),
            @XmlElement(name = "drift", type = Drift.class),
            @XmlElement(name = "fade-in", type = FadeIn.class),
            @XmlElement(name = "fade-out", type = FadeOut.class),
            @XmlElement(name = "move-in", type = MoveIn.class),
            @XmlElement(name = "move-out", type = MoveOut.class),
            @XmlElement(name = "parallax", type = Parallax.class),
            @XmlElement(name = "play", type = Play.class),
            @XmlElement(name = "rotate", type = Rotate.class),
            @XmlElement(name = "scale", type = Scale.class)
    })

    public final ObservableList<Transition> getHoverTransitions() {
        return hoverTransitions;
    }

    // layoutStrategy support

    public enum LayoutStrategy {
        ABSOLUTE,
        SIDES,
        POSITION,
    }

    private final ObjectProperty<LayoutStrategy> layoutStrategy = new SimpleObjectProperty<>(this, "layoutStrategy", LayoutStrategy.ABSOLUTE);

    public final ObjectProperty<LayoutStrategy> layoutStrategyProperty() {
        return layoutStrategy;
    }

    public final void setLayoutStrategy(LayoutStrategy layoutStrategy) {
        this.layoutStrategy.set(layoutStrategy);
    }

    public final LayoutStrategy getLayoutStrategy() {
        return layoutStrategy.get();
    }

    // slots support

    public enum Side {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }

    private final ObjectProperty<Side> side = new SimpleObjectProperty<>(this, "side", Side.TOP);

    public final ObjectProperty<Side> sideProperty() {
        return side;
    }

    public final void setSide(Side side) {
        this.side.set(side);
    }

    public final Side getSide() {
        return side.get();
    }

    // position support

    private final ObjectProperty<Pos> position = new SimpleObjectProperty<>(this, "position", Pos.CENTER);

    public final ObjectProperty<Pos> positionProperty() {
        return position;
    }

    public final void setPosition(Pos position) {
        this.position.set(position);
    }

    public final Pos getPosition() {
        return position.get();
    }

    // layoutStrategy x support

    private final DoubleProperty layoutX = new SimpleDoubleProperty(this, "layoutX", 0);

    public final DoubleProperty layoutXProperty() {
        return layoutX;
    }

    public final void setLayoutX(double x) {
        this.layoutX.set(x);
    }

    public final double getLayoutX() {
        return layoutX.get();
    }

    // layoutStrategy y support

    private final DoubleProperty layoutY = new SimpleDoubleProperty(this, "layoutY", 0);

    public final DoubleProperty layoutYProperty() {
        return layoutY;
    }

    public final void setLayoutY(double y) {
        this.layoutY.set(y);
    }

    public final double getLayoutY() {
        return layoutY.get();
    }

    // visible support

    private final BooleanProperty visible = new SimpleBooleanProperty(this, "visible", true);

    public final BooleanProperty visibleProperty() {
        return visible;
    }

    public final boolean isVisible() {
        return visible.get();
    }

    public final void setVisible(boolean visible) {
        this.visible.set(visible);
    }

    // locked support

    private final BooleanProperty locked = new SimpleBooleanProperty(this, "locked", false);

    public final BooleanProperty lockedProperty() {
        return locked;
    }

    public final boolean isLocked() {
        return locked.get();
    }

    public final void setLocked(boolean locked) {
        this.locked.set(locked);
    }

    // width support

    private final DoubleProperty width = new SimpleDoubleProperty(this, "width", 200);

    public final DoubleProperty widthProperty() {
        return width;
    }

    public final void setWidth(double width) {
        this.width.set(width);
    }

    public final double getWidth() {
        return width.get();
    }

    // height support

    private final DoubleProperty height = new SimpleDoubleProperty(this, "height", 200);

    public final DoubleProperty heightProperty() {
        return height;
    }

    public final void setHeight(double height) {
        this.height.set(height);
    }

    public final double getHeight() {
        return height.get();
    }

}
