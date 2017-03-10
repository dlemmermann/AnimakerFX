package com.animaker.view.builder;

import com.animaker.model.Presentation;
import com.animaker.model.Presentation.Layout;
import com.animaker.model.Project;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by lemmi on 09.03.17.
 */
public class PresentationSettingsView extends HBox {

    private ComboBox<Layout> layoutBox;
    private TextField widthField;
    private TextField heightField;
    private FileSelectionField imageSelector;
    private FileSelectionField videoSelector;
    private ComboBox<BackgroundRepeat> backgroundRepeatBox;
    private CheckBox videoInfiniteLoop;
    private Slider videoOpacitySlider;

    public PresentationSettingsView() {
        super(10);

        getStyleClass().add("presentation-settings-view");

        setAlignment(Pos.BASELINE_LEFT);

        layoutBox = new ComboBox<>();
        layoutBox.getItems().setAll(Layout.values());
        widthField = new TextField();
        heightField = new TextField();
        imageSelector = new FileSelectionField();
        backgroundRepeatBox = new ComboBox<>();
        backgroundRepeatBox.getItems().setAll(BackgroundRepeat.values());
        videoInfiniteLoop = new CheckBox();
        videoOpacitySlider = new Slider(0,1, 1);

        videoSelector = new FileSelectionField();

        Label layoutLabel = new Label("LayoutStrategy:");
        Label widthLabel = new Label("Width:");
        Label heightLabel = new Label("Height:");
        Label imageLabel = new Label("Image:");
        Label videoLabel = new Label("Video:");
        Label loopLabel = new Label("Loop:");
        Label opacityLabel = new Label("Opacity:");

        layoutLabel.getStyleClass().add("field-label");
        widthLabel.getStyleClass().add("field-label");
        heightLabel.getStyleClass().add("field-label");
        imageLabel.getStyleClass().add("field-label");
        videoLabel.getStyleClass().add("field-label");
        loopLabel.getStyleClass().add("field-label");
        opacityLabel.getStyleClass().add("field-label");

        getChildren().setAll(layoutLabel, layoutBox, widthLabel, widthField, heightLabel, heightField,
                imageLabel, imageSelector, backgroundRepeatBox, videoLabel, videoSelector, loopLabel, videoInfiniteLoop,
                opacityLabel, videoOpacitySlider);

        presentationProperty().addListener(it -> updateView());

        ExtensionFilter imageFilter = new ExtensionFilter("Images (.gif, .png, .jpg)", "*.gif", "*.png", "*.jpg");
        imageSelector.projectProperty().bind(projectProperty());
        imageSelector.getExtensionFilters().setAll(imageFilter);

        ExtensionFilter videoFilter = new ExtensionFilter("Videos (.mp4)", "*.mp4");
        videoSelector.projectProperty().bind(projectProperty());
        videoSelector.getExtensionFilters().setAll(videoFilter);
    }

    private void updateView() {
        Presentation presentation = getPresentation();

        layoutBox.valueProperty().unbind();
        widthField.textProperty().unbind();
        heightField.textProperty().unbind();

        if (presentation != null) {
            Bindings.bindBidirectional(layoutBox.valueProperty(), presentation.layoutProperty());
            Bindings.bindBidirectional(backgroundRepeatBox.valueProperty(), presentation.backgroundRepeatProperty());
            Bindings.bindBidirectional(videoInfiniteLoop.selectedProperty(), presentation.infiniteLoopProperty());
            Bindings.bindBidirectional(videoOpacitySlider.valueProperty(), presentation.videoOpacityProperty());
            Bindings.bindBidirectional(widthField.textProperty(), presentation.widthProperty(), new NumberStringConverter());
            Bindings.bindBidirectional(heightField.textProperty(), presentation.heightProperty(), new NumberStringConverter());
            presentation.layoutProperty().addListener(weakUpdateSizeFieldsListener);

            // background image
            String imageFileName = presentation.getImageFileName();
            if (imageFileName != null) {
                imageSelector.setFile(getProject().getFile(imageFileName));
            } else {
                imageSelector.setFile(null);
            }

            // background video
            String videoFileName = presentation.getVideoFileName();
            if (videoFileName != null) {
                videoSelector.setFile(getProject().getFile(videoFileName));
            } else {
                videoSelector.setFile(null);
            }

            presentation.imageFileNameProperty().bind(imageSelector.fileNameProperty());
            presentation.videoFileNameProperty().bind(videoSelector.fileNameProperty());
        }

        updateSizeFields();
    }

    private final InvalidationListener updateSizeFieldsListener = it -> updateSizeFields();

    private final WeakInvalidationListener weakUpdateSizeFieldsListener = new WeakInvalidationListener(updateSizeFieldsListener);

    private void updateSizeFields() {
        Presentation presentation = getPresentation();
        if (presentation != null) {
            Layout layout = presentation.getLayout();
            switch (layout) {
                case FILL:
                    widthField.setDisable(true);
                    heightField.setDisable(true);
                    break;
                case FIXED_HEIGHT:
                    widthField.setDisable(true);
                    heightField.setDisable(false);
                    break;
                case FIXED_WIDTH:
                    widthField.setDisable(false);
                    heightField.setDisable(true);
                    break;
                case FIXED_SIZE:
                    widthField.setDisable(false);
                    heightField.setDisable(false);
                    break;
            }
        }
    }

    // presentation support

    private final ObjectProperty<Presentation> presentation = new SimpleObjectProperty<>(this, "presentation");

    public final ObjectProperty<Presentation> presentationProperty() {
        return presentation;
    }

    public final void setPresentation(Presentation presentation) {
        this.presentation.set(presentation);
    }

    public final Presentation getPresentation() {
        return presentation.get();
    }

    // project support

    private final ObjectProperty<Project> project = new SimpleObjectProperty<>(this, "project");

    public final ObjectProperty<Project> projectProperty() {
        return project;
    }

    public final void setProject(Project project) {
        this.project.set(project);
    }

    public final Project getProject() {
        return project.get();
    }

}
