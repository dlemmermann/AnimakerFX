package com.animaker;

import com.animaker.model.Layer;
import com.animaker.model.Slide;
import com.animaker.model.Slider;
import com.animaker.model.transition.*;
import com.animaker.view.builder.SliderBuilderView;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by lemmi on 19.12.16.
 */
public class SliderApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SliderBuilderView view = new SliderBuilderView();

        Slider slider = new Slider("My Slider");
        slider.setLayout(Slider.SliderLayout.FIXED_SIZE);
        slider.setWidth(960);
        slider.setHeight(540);
        slider.getStylesheets().add(SliderApp.class.getResource("demo.css").toExternalForm());

//        slider.setBackgroundImage(new Image(getClass().getResource("texture.jpg").toExternalForm()));

        // slide 1 - video
        Slide slide1 = new Slide("Big Buck Bunny");
        Layer videoLayer = new Layer("Media Player");
        videoLayer.setType(Layer.LayerType.VIDEO);
        videoLayer.setVideoContent("file:///Users/lemmi/Desktop/BigBuckBunny_320x180.mp4");
        slide1.getLayers().setAll(videoLayer);

        // slide 2 - web
        Slide slide2 = new Slide("You Tube");
        Layer htmlLayer = new Layer("HTML Web View");
        htmlLayer.setType(Layer.LayerType.HTML);
        htmlLayer.setHtmlContent(SliderApp.class.getResource("youtube.html").toExternalForm());
        slide2.getLayers().setAll(htmlLayer);

        // slide 3 - image layer and text layer
        Slide slide3 = new Slide("Image and Text");
        Layer imageLayer = new Layer("Background");
        imageLayer.setType(Layer.LayerType.IMAGE);
        imageLayer.setImageContent(new Image(SliderApp.class.getResource("texture.jpg").toExternalForm()));
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

        slider.getSlides().setAll(slide3, slide2, slide1);
        view.setSlider(slider);

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
