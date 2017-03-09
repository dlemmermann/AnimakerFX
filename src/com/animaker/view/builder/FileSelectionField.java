package com.animaker.view.builder;

import com.animaker.model.Project;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

/**
 * Created by lemmi on 09.03.17.
 */
public class FileSelectionField extends HBox {

    public FileSelectionField() {
        super(5);

        setAlignment(Pos.BASELINE_LEFT);

        TextField textField = new TextField();
        textField.setEditable(false);
        textField.textProperty().bind(fileNameProperty());

        Button button = new Button("...");
        button.setOnAction(evt -> loadFile());

        getChildren().setAll(textField, button);

        fileProperty().addListener(it -> {
            File file = getFile();
            if (file != null) {
                fileName.set(file.getName());
            } else {
                fileName.set(null);
            }
        });
    }

    private FileChooser fileChooser;

    private void loadFile() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            Bindings.bindContentBidirectional(fileChooser.getExtensionFilters(), getExtensionFilters());
        }

        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            Project project = getProject();
            project.addFile(file);
            setFile(file);
        }
    }

    /**
     * Specifies the extension filters used in the displayed file dialog.
     */
    private ObservableList<ExtensionFilter> extensionFilters = FXCollections.observableArrayList();

    /**
     * Gets the extension filters used in the displayed file dialog. Only
     * one extension filter from the list is active at any time in the displayed
     * dialog and only files which correspond to this extension filter are
     * shown. The first extension filter from the list is activated when the
     * dialog is invoked. Then the user can switch the active extension filter
     * to any other extension filter from the list and in this way control the
     * set of displayed files.
     *
     * @return An observable list of the extension filters used in this dialog
     */
    public ObservableList<ExtensionFilter> getExtensionFilters() {
        return extensionFilters;
    }

    // file support

    private ObjectProperty<File> file = new SimpleObjectProperty<>(this, "file");

    public final ObjectProperty<File> fileProperty() {
        return file;
    }

    public final File getFile() {
        return file.get();
    }

    public final void setFile(File file) {
        this.file.set(file);
    }

    // file name support

    private ReadOnlyStringWrapper fileName = new ReadOnlyStringWrapper(this, "fileName");

    public ReadOnlyStringProperty fileNameProperty() {
        return fileName.getReadOnlyProperty();
    }

    public String getFileName() {
        return fileName.get();
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
