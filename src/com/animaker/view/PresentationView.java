package com.animaker.view;

import com.animaker.model.Presentation;
import com.animaker.model.Project;
import com.animaker.model.Slide;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

/**
 * Created by lemmi on 19.12.16.
 */
public class PresentationView extends Pane {

    private final Project project;
    private final Presentation presentation;
    private MediaView mediaView;

    public PresentationView(Project project, Presentation presentation) {
        this.project = Objects.requireNonNull(project);
        this.presentation = Objects.requireNonNull(presentation);

        getStyleClass().add("presentation");

        presentation.layoutProperty().addListener(it -> updateSize());
        presentation.backgroundRepeatProperty().addListener(it -> updateBackgroundImage());
        presentation.getStylesheets().addListener((Observable it) -> updateStylesheets());

        presentation.imageFileNameProperty().addListener(it -> updateBackgroundImage());
        presentation.videoFileNameProperty().addListener(it -> updateBackgroundVideo());
        presentation.infiniteLoopProperty().addListener(it -> updateBackgroundVideo());

        currentSlideProperty().addListener(it -> updateSlide());

        updateBackgroundImage();
        updateBackgroundVideo();
        updateSlide();
        updateSize();
        updateStylesheets();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);
    }

    public void destroy() {
        if (mediaView != null) {
            mediaView.getMediaPlayer().dispose();
        }
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();

        Insets insets = getInsets();

        getChildren().forEach(child -> child.resizeRelocate(insets.getLeft(), insets.getTop(),
                w - insets.getLeft() - insets.getRight(), h - insets.getTop() - insets.getBottom()));
    }

    public final Project getProject() {
        return project;
    }

    public final Presentation getPresentation() {
        return presentation;
    }

    // current slide support

    private final ObjectProperty<Slide> currentSlide = new SimpleObjectProperty<>(this, "currentSlide");

    public final ObjectProperty<Slide> currentSlideProperty() {
        return currentSlide;
    }

    public final void setCurrentSlide(Slide slide) {
        this.currentSlide.set(slide);
    }

    public final Slide getCurrentSlide() {
        return currentSlide.get();
    }

    public enum Status {
        STOPPED,
        PAUSED,
        PLAY,
    }

    // status support

    private final ObjectProperty<Status> status = new SimpleObjectProperty<>(this, "status", Status.STOPPED);

    public final ObjectProperty<Status> statusProperty() {
        return status;
    }

    public final void setStatus(Status status) {
        this.status.set(status);
    }

    public final Status getStatus() {
        return status.get();
    }

    private void updateStylesheets() {
        getStylesheets().setAll(presentation.getStylesheets());
    }

    private void updateBackgroundImage() {
        String fileName = presentation.getImageFileName();
        if (fileName != null) {
            getStyleClass().remove("empty-background-presentation");
            Project project = getProject();
            File file = project.getFile(fileName);
            try (FileInputStream in = new FileInputStream(file)) {
                final Image image = new Image(in);
                final Presentation presentation = getPresentation();
                final BackgroundRepeat repeat = presentation.getBackgroundRepeat();
                BackgroundSize size = BackgroundSize.DEFAULT;
                if (repeat.equals(BackgroundRepeat.NO_REPEAT)) {
                    size = new BackgroundSize(100, 1000, true, true, false, true);
                }
                final BackgroundImage backgroundImage = new BackgroundImage(image, repeat, repeat, BackgroundPosition.CENTER, size);
                setBackground(new Background(backgroundImage));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            getStyleClass().add("empty-background-presentation");
        }
    }

    private synchronized void updateBackgroundVideo() {
        if (mediaView != null) {
            mediaView.getMediaPlayer().stop();
        }

        String videoContent = presentation.getVideoFileName();
        if (videoContent != null) {
            try {
                File videoFile = project.getFile(videoContent);
                Media media = new Media(videoFile.toPath().toUri().toURL().toExternalForm());
                MediaPlayer player = new MediaPlayer(media);

                if (presentation.getInfiniteLoop()) {
                    player.setCycleCount(MediaPlayer.INDEFINITE);
                }

                mediaView = new MediaView(player);
                mediaView.opacityProperty().bind(presentation.videoOpacityProperty());
                mediaView.setPreserveRatio(false);
                mediaView.fitHeightProperty().bind(heightProperty());
                mediaView.fitWidthProperty().bind(widthProperty());

                player.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            mediaView = null;
        }

        updateSlide();
    }

    private void updateSlide() {
        Slide slide = getCurrentSlide();

        if (mediaView != null) {
            getChildren().setAll(mediaView);
        } else {
            getChildren().clear();
        }

        if (slide != null) {
            SlideView slideView = new SlideView(this, slide);
            StackPane.setAlignment(slideView, Pos.CENTER);
            getChildren().add(slideView);
        }
    }

    private void updateSize() {
        // first unbind everything
        prefWidthProperty().unbind();
        minWidthProperty().unbind();
        maxWidthProperty().unbind();
        prefHeightProperty().unbind();
        minHeightProperty().unbind();
        maxHeightProperty().unbind();

        switch (presentation.getLayout()) {
            case FILL:
                setMinSize(0, 0);
                setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                break;
            case FIXED_HEIGHT:
                prefHeightProperty().bind(presentation.heightProperty());
                minHeightProperty().bind(presentation.heightProperty());
                maxHeightProperty().bind(presentation.heightProperty());
                setMinWidth(0);
                setMaxWidth(Double.MAX_VALUE);
                break;
            case FIXED_WIDTH:
                prefWidthProperty().bind(presentation.widthProperty());
                minWidthProperty().bind(presentation.widthProperty());
                maxWidthProperty().bind(presentation.widthProperty());
                setMinHeight(0);
                setMaxHeight(Double.MAX_VALUE);
                break;
            case FIXED_SIZE:
                prefWidthProperty().bind(presentation.widthProperty());
                minWidthProperty().bind(presentation.widthProperty());
                maxWidthProperty().bind(presentation.widthProperty());
                prefHeightProperty().bind(presentation.heightProperty());
                minHeightProperty().bind(presentation.heightProperty());
                maxHeightProperty().bind(presentation.heightProperty());
                break;
        }
    }
}
