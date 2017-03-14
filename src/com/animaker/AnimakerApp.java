package com.animaker;

import com.animaker.model.Layer;
import com.animaker.model.Presentation;
import com.animaker.model.Presentation.Layout;
import com.animaker.model.Slide;
import com.animaker.model.transition.*;
import com.animaker.view.builder.Workbench;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by lemmi on 19.12.16.
 */
public class AnimakerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Workbench view = new Workbench();

        Presentation presentation = new Presentation("My Presentation");
        presentation.setLayout(Layout.FIXED_SIZE);
        presentation.setWidth(960);
        presentation.setHeight(540);
        presentation.getStylesheets().add(AnimakerApp.class.getResource("demo.css").toExternalForm());

//        presentation.setBackgroundImage(new Image(getClass().getResource("texture.jpg").toExternalForm()));

        // slide 1 - video
        Slide slide1 = new Slide("Big Buck Bunny");
        Layer videoLayer = new Layer("Media Player");
        videoLayer.setType(Layer.LayerType.VIDEO);
//        videoLayer.setVideoFileName("file:///Users/lemmi/Desktop/bunny.mp4");
        videoLayer.setVideoFileName("file:///Users/lemmi/Desktop/slow-motion.mp4");
        slide1.getLayers().setAll(videoLayer);
        videoLayer.getOpeningTransitions().add(new Play());

        // slide 2 - web
        Slide slide2 = new Slide("You Tube");
        Layer htmlLayer = new Layer("HTML Web View");
        htmlLayer.setType(Layer.LayerType.HTML);
        htmlLayer.setHtmlContent(AnimakerApp.class.getResource("youtube.html").toExternalForm());
        slide2.getLayers().setAll(htmlLayer);

        // slide 3 - image layer and text layer
        Slide slide3 = new Slide("Image and Text");
        Layer imageLayer = new Layer("Background");
        imageLayer.setType(Layer.LayerType.IMAGE);
        Layer textLayer = new Layer("Text");
        textLayer.setTextContent("Hello World");
        textLayer.getStyleClass().add("fancy-label");
        textLayer.setType(Layer.LayerType.TEXT);
        slide3.getLayers().setAll(imageLayer, textLayer);

        FadeIn fadeIn = new FadeIn();
        Rotate rotate = new Rotate();
        rotate.setRotation(-1080);
        rotate.setDelay(Duration.seconds(2));
        rotate.setDuration(Duration.seconds(3));

        MoveIn moveIn = new MoveIn();
        moveIn.setDuration(Duration.seconds(3));
        moveIn.setDirection(MoveIn.TransitionDirection.BOTTOM_LEFT_TO_TOP_RIGHT);
        moveIn.setInterpolator(Interpolator.EASE_OUT);

        MoveOut moveOut = new MoveOut();
        moveOut.setInterpolator(Interpolator.EASE_IN);
        moveOut.setDelay(Duration.seconds(4));
        moveOut.setDuration(Duration.seconds(2));
        moveOut.setDirection(MoveOut.TransitionDirection.TOP_LEFT_TO_BOTTOM_RIGHT);

        Scale scale = new Scale();
        scale.setScaleX(2);
        FadeOut fadeOut = new FadeOut();
        fadeOut.setDelay(Duration.seconds(4));
        fadeOut.setDuration(Duration.seconds(1));
        textLayer.getOpeningTransitions().setAll(moveIn, fadeIn, rotate, scale, fadeOut, moveOut);

        presentation.getSlides().setAll(slide3, slide2, slide1);
        //view.setPresentation(presentation);

        view.projectProperty().addListener(it -> primaryStage.setTitle("AnimakerFX: " + view.getProject().getName()));

        Scene scene = new Scene(view);
        scene.focusOwnerProperty().addListener(it -> System.out.println("focus owner: " + scene.getFocusOwner()));
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
