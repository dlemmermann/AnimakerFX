package com.animaker;

import com.animaker.model.Presentation;
import com.animaker.model.Presentation.Layout;
import com.animaker.view.builder.Workbench;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by lemmi on 19.12.16.
 */
public class AnimakerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Workbench view = new Workbench();

        Presentation presentation = new Presentation();
        presentation.setName("My Presentation");
        presentation.setLayout(Layout.FIXED_SIZE);
        presentation.setWidth(960);
        presentation.setHeight(540);
        presentation.getStylesheets().add(AnimakerApp.class.getResource("demo.css").toExternalForm());

        view.projectProperty().addListener(it -> primaryStage.setTitle("AnimakerFX: " + view.getProject().getName()));

        Scene scene = new Scene(view);
        primaryStage.setTitle("AnimakerFX");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1760);
        primaryStage.setHeight(990);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
