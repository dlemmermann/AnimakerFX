package com.animaker.view.builder;

import com.animaker.model.Layer;
import com.animaker.model.Presentation;
import com.animaker.model.Project;
import com.animaker.model.Slide;
import com.animaker.view.LayerView;
import com.animaker.view.PresentationView;
import com.animaker.view.PresentationView.Status;
import com.animaker.view.builder.layer.LayerSettingsTabPane;
import com.animaker.view.builder.layer.LayersPaletteView;
import com.animaker.view.builder.slide.SlidesPaletteView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
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
import org.scenicview.ScenicView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/**
 * Created by lemmi on 19.12.16.
 */
public class Workbench extends StackPane {

    public static final String PREF_KEY = "last.animation.project";
    public static final String PREF_SEPARATOR = "!";

    //private PropertySheet propertySheet;
    private SlidesPaletteView slidesPaletteView;
    private LayersPaletteView layersPaletteView;
    private PresentationView presentationView;
    private MasterDetailPane presentationMasterDetailPane;
    private LayerSettingsTabPane layerSettingsView;

    private Button playSlide;

    public Workbench() {
        getStyleClass().add("workbench");

        getStylesheets().add(Workbench.class.getResource("styles.css").toExternalForm());

        MenuBar menuBar = createMenuBar();

//        propertySheet = new PropertySheet();
        slidesPaletteView = new SlidesPaletteView(this);
        slidesPaletteView.setPrefHeight(200);
        layersPaletteView = new LayersPaletteView(this);
        layerSettingsView = new LayerSettingsTabPane(this);

        presentationMasterDetailPane = new MasterDetailPane();
        presentationMasterDetailPane.setDetailSide(Side.BOTTOM);
        presentationMasterDetailPane.setDividerPosition(.7);
        presentationMasterDetailPane.setDetailNode(layerSettingsView);

        MasterDetailPane leftHandSide = new MasterDetailPane();
        leftHandSide.setDetailSide(Side.TOP);
        leftHandSide.setDividerPosition(.2);
        leftHandSide.setDetailNode(slidesPaletteView);
        leftHandSide.setMasterNode(layersPaletteView);

        MasterDetailPane rightHandSide = new MasterDetailPane();
        rightHandSide.setDetailSide(Side.RIGHT);
        //rightHandSide.setDetailNode(propertySheet);
        rightHandSide.setShowDetailNode(false);
        rightHandSide.setMasterNode(presentationMasterDetailPane);

        MasterDetailPane centerPane = new MasterDetailPane();
        centerPane.setDetailSide(Side.LEFT);
        centerPane.setDividerPosition(.20);
        centerPane.setDetailNode(leftHandSide);
        centerPane.setMasterNode(rightHandSide);

        ToolBar toolBar = createToolBar();
        PresentationSettingsView presentationSettingsView = new PresentationSettingsView();
        presentationSettingsView.projectProperty().bind(projectProperty());
        presentationSettingsView.presentationProperty().bind(presentationProperty());

        VBox headerBox = new VBox();
        headerBox.setFillWidth(true);
        headerBox.getChildren().setAll(toolBar, presentationSettingsView);

        BorderPane wrapper = new BorderPane();
        wrapper.setTop(headerBox);
        wrapper.setCenter(centerPane);

        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.getChildren().setAll(menuBar, wrapper);
        VBox.setVgrow(menuBar, Priority.NEVER);
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        getChildren().add(vbox);

        slidesPaletteView.presentationProperty().bind(presentationProperty());
        layersPaletteView.slideProperty().bind(slidesPaletteView.selectedSlideProperty());

        Bindings.bindBidirectional(slidesPaletteView.selectedSlideProperty(), selectedSlideProperty());
        Bindings.bindBidirectional(layersPaletteView.selectedLayerProperty(), selectedLayerProperty());

        selectedSlideProperty().addListener(it -> updateSlide());

        setPrefSize(1500, 800);

        selectedSlideProperty().addListener(it -> {
            Slide slide = getSelectedSlide();
            if (slide != null) {
               // propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getSelectedSlide()));
            }
        });

        selectedLayerProperty().addListener(it -> {
            Layer layer = getSelectedLayer();
            if (layer != null) {
//                propertySheet.getItems().
//                        setAll(BeanPropertyUtils.getProperties(getSelectedLayer()));
            }
        });

        projectProperty().addListener(it -> savePreferences());

        loadPreferences();
        updateSlide();
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

    // selected slide support

    private final ObjectProperty<Slide> selectedSlide = new SimpleObjectProperty<>(this, "selectedSlide");

    public final ObjectProperty<Slide> selectedSlideProperty() {
        return selectedSlide;
    }

    public final void setSelectedSlide(Slide slide) {
        this.selectedSlide.set(slide);
    }

    public final Slide getSelectedSlide() {
        return selectedSlide.get();
    }

    // selected layer support

    private final ObjectProperty<Layer> selectedLayer = new SimpleObjectProperty<>(this, "selectedLayer");

    public final ObjectProperty<Layer> selectedLayerProperty() {
        return selectedLayer;
    }

    public final void setSelectedLayer(Layer layer) {
        this.selectedLayer.set(layer);
    }

    public final Layer getSelectedLayer() {
        return selectedLayer.get();
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

        MenuItem scenicViewItem = new MenuItem("Scenic View");
        scenicViewItem.setOnAction(evt -> ScenicView.show(this));
        fileMenu.getItems().add(scenicViewItem);

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
        Project project = pane.showAndWait(getScene().getWindow());
        newProject(project);
    }

    private void newProject(Project project) {
        if (project == null) {
            return;
        }
        setProject(project);
        Presentation presentation = new Presentation();
        presentation.setName(project.getName());
        setPresentation(presentation);
        savePresentation();
    }

    private void savePresentation() {
        try {
            Project project = getProject();
            Presentation presentation = getPresentation();
            JAXBContext ctx = JAXBContext.newInstance(Presentation.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(presentation, getPresentationFile(project));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private File getPresentationFile(Project project) {
        return new File(project.getLocation(), project.getName() + ".xml");
    }

    private FileChooser fileChooser = new FileChooser();

    private void loadPresentation() {
        final File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (file != null) {
            loadPresentation(file);
        }
    }

    private void loadPresentation(File file) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Presentation.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Presentation presentation = (Presentation) unmarshaller.unmarshal(file);
            Project project = new Project(presentation.getName(), file.getParentFile().getAbsolutePath());
            setProject(project);
            setPresentation(presentation);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void savePreferences() {
        Project project = getProject();
        if (project != null) {
            Preferences.userRoot().put(PREF_KEY, project.getName() + PREF_SEPARATOR + project.getLocation());
        } else {
            Preferences.userRoot().remove(PREF_KEY);
        }
    }

    private void loadPreferences() {
        String projectString = Preferences.userRoot().get(PREF_KEY, null);
        if (projectString != null) {
            StringTokenizer st = new StringTokenizer(projectString, PREF_SEPARATOR);
            try {
                String name = st.nextToken();
                String location = st.nextToken();
                Project project = new Project(name, location);
                loadPresentation(getPresentationFile(project));
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Unable to restore last project!");
            }
        }
    }

    private void showError(String text) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(text);
        alert.setHeaderText("An error has occurred.");
        alert.showAndWait();
    }

    private void addSlide() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Slide");
        dialog.setHeaderText("Add a new slide to the current presentation.");
        dialog.setContentText("Presentation Name:");
        dialog.showAndWait().ifPresent(name -> {
            Slide slide = new Slide(name);
            getPresentation().getSlides().add(slide);
            setSelectedSlide(slide);
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
            Bindings.unbindBidirectional(presentationView.currentSlideProperty(), selectedSlideProperty());
            presentationView.destroy();
        }

        Project project = getProject();
        Presentation presentation = getPresentation();

        if (project == null || presentation == null) {
            return;
        }

        presentationView = new PresentationView(project, presentation);

        installHandlers(presentationView);

        //presentationView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getPresentation())));
        Bindings.bindBidirectional(presentationView.currentSlideProperty(), selectedSlideProperty());

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
                StackPane presentationWrapper = new StackPane();
                presentationWrapper.getStyleClass().add("presentation-wrapper");
                presentationWrapper.getChildren().add(presentationView);
                presentationMasterDetailPane.setMasterNode(presentationWrapper);
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
                presentationMasterDetailPane.setMasterNode(scrollPane);
                break;
        }
    }

    private double mouseX, mouseY;

    private void installHandlers(PresentationView presentationView) {

        presentationView.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            if (evt.getClickCount() == 1 && evt.getButton().equals(MouseButton.PRIMARY)) {
                Object object = evt.getTarget();
                if (object instanceof LayerView) {
                    LayerView view = (LayerView) object;
                    setSelectedLayer(view.getLayer());
                    mouseX = evt.getSceneX() ;
                    mouseY = evt.getSceneY() ;
                }
            }
        });

        presentationView.addEventFilter(MouseEvent.MOUSE_DRAGGED, evt -> {
            Object object = evt.getTarget();
            if (object instanceof LayerView) {
                LayerView view = (LayerView) object;
                double deltaX = evt.getSceneX() - mouseX ;
                double deltaY = evt.getSceneY() - mouseY ;
                view.relocate(view.getLayoutX() + deltaX, view.getLayoutY() + deltaY);
                mouseX = evt.getSceneX() ;
                mouseY = evt.getSceneY() ;
            }
        });
    }
}
