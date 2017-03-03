package com.animaker.model;

import com.animaker.model.transition.Transition;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="Layer")
public class Layer extends ModelObject {

    public enum LayerType {
        IMAGE,
        TEXT,
        VIDEO,
        FXML,
        CODE,
        HTML
    }

    public Layer() {
    }

    public Layer(String name) {
        super(name);
    }

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

    private final DoubleProperty scaleX = new SimpleDoubleProperty(this, "scaleX", 0);

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

    private final DoubleProperty scaleY = new SimpleDoubleProperty(this, "scaleY", 0);

    public final DoubleProperty scaleYProperty() {
        return scaleY;
    }

    public final double getScaleY() {
        return scaleY.get();
    }

    public final void setScaleY(double scale) {
        this.scaleY.set(scale);
    }

    // opening transition support

    private final ObservableList<Transition> openingTransitions = FXCollections.observableArrayList();

    public final ObservableList<Transition> getOpeningTransitions() {
        return openingTransitions;
    }

    private final BooleanProperty enableOpeningTransitions = new SimpleBooleanProperty(this, "enableOpeningTransitions", false);

    public final BooleanProperty enableOpeningTransitionsProperty() {
        return enableOpeningTransitions;
    }

    public final void setEnableOpeningTransitions(boolean enableOpeningTransitions) {
        this.enableOpeningTransitions.set(enableOpeningTransitions);
    }

    public final boolean isEnableOpeningTransitions() {
        return enableOpeningTransitions.get();
    }

    // loop transition support

    // ending transition support

    // hover transition support

    // parallax transition support

    // type support

    private final ObjectProperty<LayerType> type = new SimpleObjectProperty<>(this, "layer", LayerType.IMAGE);

    public final ObjectProperty<LayerType> typeProperty() {
        return type;
    }

    public final LayerType getType() {
        return type.get();
    }

    public final void setType(LayerType type) {
        this.type.set(type);
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

    // text content support

    private final StringProperty textContent = new SimpleStringProperty(this, "textContent");

    public final StringProperty textContentProperty() {
        return textContent;
    }

    public final String getTextContent() {
        return textContent.get();
    }

    public final void setTextContent(String content) {
        this.textContent.set(content);
    }

    // image content support

    private final ObjectProperty<Image> imageContent = new SimpleObjectProperty<>(this, "imageContent");

    public final ObjectProperty imageContentProperty() {
        return imageContent;
    }

    public final Image getImageContent() {
        return imageContent.get();
    }

    public final void setImageContent(Image content) {
        this.imageContent.set(content);
    }

    // video content support

    private final StringProperty videoContent = new SimpleStringProperty(this, "videoContent");

    public final StringProperty videoContentProperty() {
        return videoContent;
    }

    public final String getVideoContent() {
        return videoContent.get();
    }

    public final void setVideoContent(String content) {
        this.videoContent.set(content);
    }

    // video content support / preserve ratio

    private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio", true);

    public final BooleanProperty preserveRatioProperty() {
        return preserveRatio;
    }

    public final void setPreserveRatio(boolean preserveRatio) {
        this.preserveRatio.set(preserveRatio);
    }

    public final boolean isPreserveRatio() {
        return preserveRatio.get();
    }

    // fxml content support

    private final StringProperty fxmlContent = new SimpleStringProperty(this, "fxmlContent");

    public final StringProperty fxmlContentProperty() {
        return fxmlContent;
    }

    public final String getFxmlContent() {
        return fxmlContent.get();
    }

    public final void setFxmlContent(String content) {
        this.fxmlContent.set(content);
    }

    // code content support

    private final StringProperty codeContent = new SimpleStringProperty(this, "codeContent");

    public final StringProperty codeContentProperty() {
        return codeContent;
    }

    public final String getCodeContent() {
        return codeContent.get();
    }

    public final void setCodeContent(String content) {
        this.codeContent.set(content);
    }

    // web content support

    private final StringProperty htmlContent = new SimpleStringProperty(this, "htmlContent");

    public final StringProperty htmlContentProperty() {
        return htmlContent;
    }

    public final String getHtmlContent() {
        return htmlContent.get();
    }

    public final void setHtmlContent(String content) {
        this.htmlContent.set(content);
    }
}
