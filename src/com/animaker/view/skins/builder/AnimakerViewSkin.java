package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.model.Presentation;
import com.animaker.model.Slide;
import com.animaker.view.PresentationView;
import com.animaker.view.PresentationView.Status;
import com.animaker.view.builder.AnimakerView;
import com.animaker.view.builder.LayerSettingsView;
import com.animaker.view.builder.LayersPaletteView;
import com.animaker.view.builder.NewProjectPane;
import com.animaker.view.builder.Project;
import com.animaker.view.builder.SlidesPaletteView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by lemmi on 19.12.16.
 */
public class AnimakerViewSkin extends SkinBase<AnimakerView> {

    private final PropertySheet propertySheet;
    private SlidesPaletteView slidesPaletteView;
    private LayersPaletteView layersPaletteView;
    private PresentationView presentationView;
    private MasterDetailPane centerPane;
    private Button playSlide;

    public AnimakerViewSkin(AnimakerView view) {
        super(view);

        MenuBar menuBar = createMenuBar();

        propertySheet = new PropertySheet();
        slidesPaletteView = new SlidesPaletteView();
        slidesPaletteView.setPrefHeight(200);
        layersPaletteView = new LayersPaletteView();
        centerPane = new MasterDetailPane();
        centerPane.setDetailSide(Side.BOTTOM);
        centerPane.setDividerPosition(.7);

        MasterDetailPane leftHandSide = new MasterDetailPane();
        leftHandSide.setDetailSide(Side.TOP);
        leftHandSide.setDividerPosition(.2);
        leftHandSide.setDetailNode(slidesPaletteView);
        leftHandSide.setMasterNode(layersPaletteView);

        LayerSettingsView layerSettingsView = new LayerSettingsView();
        layerSettingsView.layerProperty().bind(view.selectedLayerProperty());
        centerPane.setDetailNode(layerSettingsView);

        ToolBar toolBar = createToolBar();

        MasterDetailPane rightHandSide = new MasterDetailPane();
        rightHandSide.setDetailSide(Side.RIGHT);
        rightHandSide.setDetailNode(propertySheet);
        rightHandSide.setMasterNode(centerPane);

        MasterDetailPane centerPane = new MasterDetailPane();
        centerPane.setDetailSide(Side.LEFT);
        centerPane.setDividerPosition(.20);
        centerPane.setDetailNode(leftHandSide);
        centerPane.setMasterNode(rightHandSide);

        BorderPane wrapper = new BorderPane();
        wrapper.setTop(toolBar);
        wrapper.setCenter(centerPane);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.getChildren().setAll(menuBar, wrapper);
        VBox.setVgrow(menuBar, Priority.NEVER);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        getChildren().add(vbox);

        slidesPaletteView.presentationProperty().bind(view.presentationProperty());
        layersPaletteView.slideProperty().bind(slidesPaletteView.selectedSlideProperty());

        Bindings.bindBidirectional(slidesPaletteView.selectedSlideProperty(), view.selectedSlideProperty());
        Bindings.bindBidirectional(layersPaletteView.selectedLayerProperty(), view.selectedLayerProperty());


        view.selectedSlideProperty().addListener(it -> updateSlide());

        view.setPrefSize(1500, 800);

        view.selectedSlideProperty().addListener(it -> {
            Slide slide = view.getSelectedSlide();
            if (slide != null) {
                propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(view.getSelectedSlide()));
            }
        });

        view.selectedLayerProperty().addListener(it -> {
            Layer layer = view.getSelectedLayer();
            if (layer != null) {
                propertySheet.getItems().
                        setAll(BeanPropertyUtils.getProperties(view.getSelectedLayer()));
            }
        });

    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem newProjectItem = new MenuItem("New Presentation...");
        newProjectItem.setOnAction(evt -> newProject());
        fileMenu.getItems().add(newProjectItem);

        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction(evt -> Platform.exit());
        quitItem.setAccelerator(KeyCombination.valueOf("SHORTCUT+Q"));
        fileMenu.getItems().add(quitItem);

        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private ToolBar createToolBar() {
        ToolBar bar = new ToolBar();

        // save
        Button save = new Button("Save");
        save.setOnAction(evt -> savePresentation());
        bar.getItems().add(save);

        // load
        Button load = new Button("Load");
        load.setOnAction(evt -> loadPresentation());
        bar.getItems().add(load);

        // add slide
        Button addSlide = new Button("Add Slide");
        addSlide.setOnAction(evt -> addSlide());
        bar.getItems().add(addSlide);

        // play slide
        playSlide = new Button("Play");
        playSlide.setOnAction(evt -> {
            if (playSlide.getText().equals("Pause")) {
                pauseSlide();
            } else {
                playSlide();
            }
        });

        bar.getItems().add(playSlide);

        // stop slide
        Button stopSlide = new Button("Stop");
        stopSlide.setOnAction(evt -> stop());
        bar.getItems().add(stopSlide);

        return bar;
    }

    private void newProject() {
        NewProjectPane pane = new NewProjectPane();
        Project project = pane.showAndWait(getSkinnable().getScene().getWindow());
        newProject(project);
    }

    private void newProject(Project project) {
        getSkinnable().setProject(project);
        Presentation presentation = new Presentation();
        presentation.setName(project.getName());
        getSkinnable().setPresentation(presentation);
        savePresentation();
    }

    private void savePresentation() {
        try {
            Project project = getSkinnable().getProject();
            Presentation presentation = getSkinnable().getPresentation();
            JAXBContext ctx = JAXBContext.newInstance(Presentation.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(presentation, new File(project.getLocation(), project.getName() + ".xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private FileChooser fileChooser = new FileChooser();

    private void loadPresentation() {
        final File file = fileChooser.showOpenDialog(getSkinnable().getScene().getWindow());
        if (file != null) {
            loadPresentation(file);
        }
    }

    private void loadPresentation(File file) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Presentation.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Presentation presentation = (Presentation) unmarshaller.unmarshal(file);
            getSkinnable().setPresentation(presentation);
            Project project = new Project(presentation.getName(), file.getParentFile().getAbsolutePath());
            getSkinnable().setProject(project);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void addSlide() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Slide");
        dialog.setHeaderText("Add a new slide to the current presentation.");
        dialog.setContentText("Presentation Name:");
        dialog.showAndWait().ifPresent(name -> {
            Slide slide = new Slide(name);
            getSkinnable().getPresentation().getSlides().add(slide);
            getSkinnable().setSelectedSlide(slide);
        });
    }

    private void playSlide() {
        presentationView.setStatus(Status.PLAY);
    }

    private void pauseSlide() {
        presentationView.setStatus(Status.PAUSED);
    }

    private void stop() {
        presentationView.setStatus(Status.STOPPED);
    }

    private void updateSlide() {
        if (presentationView != null) {
            Bindings.unbindBidirectional(presentationView.currentSlideProperty(), getSkinnable().selectedSlideProperty());
        }

        Presentation presentation = getSkinnable().getPresentation();
        presentationView = new PresentationView(presentation);
        presentationView.addEventFilter(MouseEvent.MOUSE_CLICKED,
                evt -> propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getSkinnable().getPresentation())));
        Bindings.bindBidirectional(presentationView.currentSlideProperty(), getSkinnable().selectedSlideProperty());

        presentationView.statusProperty().addListener(it -> {
            switch (presentationView.getStatus()) {
                case PLAY:
                    playSlide.setText("Pause");
                    break;
                case PAUSED:
                    playSlide.setText("Resume");
                    break;
                case STOPPED:
                    playSlide.setText("Play");
                    break;
            }
        });

        switch (presentation.getLayout()) {
            case FILL:
                BorderPane.setAlignment(presentationView, Pos.CENTER);
                centerPane.setMasterNode(presentationView);
                break;
            case FIXED_HEIGHT:
            case FIXED_WIDTH:
            case FIXED_SIZE:
                StackPane content = new StackPane();
                content.getChildren().add(presentationView);
                StackPane.setAlignment(presentationView, Pos.CENTER);
                presentationView.setEffect(new DropShadow(20, Color.BLACK));
                ScrollPane scrollPane = new ScrollPane(content);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);
                centerPane.setMasterNode(scrollPane);
                break;
        }
    }
}
