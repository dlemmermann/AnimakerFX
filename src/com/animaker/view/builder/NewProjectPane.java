package com.animaker.view.builder;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.File;

public class NewProjectPane extends GridPane {

    private final TextField nameField;
    private final TextField locationField;
    private final Label validationLabel;

    public NewProjectPane() {
        getStyleClass().add("new-project-pane");

        Label nameLabel = new Label("Name:");
        Label locationLabel = new Label("Location:");
        validationLabel = new Label("OK");

        nameField = new TextField();
        locationField = new TextField();

        Button locationButton = new Button("...");
        locationButton.setOnAction(evt -> openFolder());

        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);

        RowConstraints row2 = new RowConstraints();
        row2.setValignment(VPos.CENTER);

        RowConstraints row3 = new RowConstraints();
        row3.setValignment(VPos.CENTER);

        getRowConstraints().setAll(row1, row2, row3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(Region.USE_PREF_SIZE);
        col1.setMinWidth(Region.USE_PREF_SIZE);
        col1.setHgrow(Priority.NEVER);
        col1.setHalignment(HPos.RIGHT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMaxWidth(Region.USE_PREF_SIZE);
        col3.setMinWidth(Region.USE_PREF_SIZE);
        col3.setHgrow(Priority.NEVER);

        getColumnConstraints().setAll(col1, col2, col3);

        add(nameLabel, 0, 0);
        add(nameField, 1, 0);
        add(locationLabel, 0, 1);
        add(locationField, 1, 1);
        add(locationButton, 2, 1);
        add(validationLabel, 0, 2);

        GridPane.setColumnSpan(validationLabel, 3);
        GridPane.setHalignment(validationLabel, HPos.LEFT);
    }

    /*
    Work-around for bug https://bitbucket.org/controlsfx/controlsfx/issues/539/multiple-dialog-fields-with-validation
     */
    public void installValidation() {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(nameField, Validator.createEmptyValidator("A project name is required!"));
        validationSupport.registerValidator(locationField, Validator.createEmptyValidator("A project location is required!"));
        validationSupport.registerValidator(locationField, (Control c, String newText) -> {

            File file = new File(newText);
            if (!file.exists()) {
                return ValidationResult.fromError(c, "Location does not exist!");
            }

            if (!file.isDirectory()) {
                return ValidationResult.fromError(c, "Location must be a directory, not a file!");
            }

            return null;
        });

        validationSupport.validationResultProperty().addListener( (o, oldValue, newValue) -> {
            if (newValue.getMessages().isEmpty()) {
                validationLabel.setText("Good");
            } else {
                validationLabel.setText("Bad");
            }
        });
    }

    private DirectoryChooser directoryChooser = new DirectoryChooser();

    private void openFolder() {
        directoryChooser.setTitle("Project Location");
        final File file = directoryChooser.showDialog(getScene().getWindow());
        if (file != null) {
            locationField.setText(file.getAbsolutePath());
        }
    }

    public final Project showAndWait(Window owner) {
        Dialog<Project> dialog = new Dialog<>();
        dialog.setTitle("New Project");
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(this);
        dialog.setWidth(500);
        dialog.initOwner(owner);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Project(nameField.getText(), locationField.getText());
            }
            return null;
        });
        // work-around for https://bitbucket.org/controlsfx/controlsfx/issues/539/multiple-dialog-fields-with-validation
        Platform.runLater(() -> installValidation());
        return dialog.showAndWait().get();
    }
}
