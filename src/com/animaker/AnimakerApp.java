package com.animaker;

import com.animaker.model.Element;
import com.animaker.model.Presentation;
import com.animaker.model.Presentation.Layout;
import com.animaker.model.Slide;
import com.animaker.model.elements.HTMLElement;
import com.animaker.model.elements.ImageElement;
import com.animaker.model.elements.TextElement;
import com.animaker.model.elements.VideoElement;
import com.animaker.model.transition.*;
import com.animaker.view.builder.Workbench;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

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
