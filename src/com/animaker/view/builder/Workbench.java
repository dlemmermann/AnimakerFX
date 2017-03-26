package com.animaker.view.builder;

import com.animaker.model.Element;
import com.animaker.model.Element.LayoutStrategy;
import com.animaker.model.Presentation;
import com.animaker.model.Project;
import com.animaker.model.Slide;
import com.animaker.model.elements.ImageElement;
import com.animaker.model.elements.VideoElement;
import com.animaker.model.transition.Play;
import com.animaker.view.ElementView;
import com.animaker.view.PresentationView;
import com.animaker.view.PresentationView.Status;
import com.animaker.view.builder.ResizeHandles.ResizeHandle;
import com.animaker.view.builder.element.ElementsPaletteView;
import com.animaker.view.builder.element.ElementSettingsTabPane;
import com.animaker.view.builder.slide.SlidesPaletteView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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

    private PropertySheet propertySheet;
    private SlidesPaletteView slidesPaletteView;
    private ElementsPaletteView elementsPaletteView;
    private MasterDetailPane presentationMasterDetailPane;
    private ElementSettingsTabPane layerSettingsView;

    private Button playSlideButton;
    private Node playIcon;
    private Node pauseIcon;

    public Workbench() {
        getStyleClass().add("workbench");

        getStylesheets().add(Workbench.class.getResource("styles.css").toExternalForm());

        MenuBar menuBar = createMenuBar();

        propertySheet = new PropertySheet();
        slidesPaletteView = new SlidesPaletteView(this);
        slidesPaletteView.setPrefHeight(200);
        elementsPaletteView = new ElementsPaletteView(this);
        layerSettingsView = new ElementSettingsTabPane(this);

        presentationMasterDetailPane = new MasterDetailPane();
        presentationMasterDetailPane.setDetailSide(Side.BOTTOM);
        presentationMasterDetailPane.setDividerPosition(.7);
        presentationMasterDetailPane.setDetailNode(layerSettingsView);

        MasterDetailPane leftHandSide = new MasterDetailPane();
        leftHandSide.setDetailSide(Side.TOP);
        leftHandSide.setDividerPosition(.2);
        leftHandSide.setDetailNode(slidesPaletteView);
        leftHandSide.setMasterNode(elementsPaletteView);

        MasterDetailPane rightHandSide = new MasterDetailPane();
        rightHandSide.setDetailSide(Side.RIGHT);
        rightHandSide.setDetailNode(propertySheet);
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
        elementsPaletteView.slideProperty().bind(slidesPaletteView.selectedSlideProperty());

        Bindings.bindBidirectional(slidesPaletteView.selectedSlideProperty(), selectedSlideProperty());
        Bindings.bindBidirectional(elementsPaletteView.selectedLayerProperty(), selectedElementProperty());

        selectedSlideProperty().addListener(it -> updateSlide());

        setPrefSize(1500, 800);

        selectedSlideProperty().addListener(it -> {
            Slide slide = getSelectedSlide();
            if (slide != null) {
                    propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getSelectedSlide()));
            }
        });

        selectedElementProperty().addListener(it -> {
            Element element = getSelectedElement();
            if (element != null) {
                propertySheet.getItems().
                        setAll(BeanPropertyUtils.getProperties(getSelectedElement()));
            }
        });

        projectProperty().addListener(it -> savePreferences());

        loadPreferences();
        updateSlide();
    }

    // resize support

    public final void initResize(ElementView elementView) {
        getPresentationView().getChildren().removeIf(child -> child instanceof ResizeHandles);
        ResizeHandles resizeHandles = new ResizeHandles(this, elementView);
        getPresentationView().getChildren().add(resizeHandles);
        resizeHandles.toFront();
    }

    public final void stopResize() {
        stopResize(null);
    }

    public final void stopResize(ElementView elementView) {
        if (elementView == null) {
            getPresentationView().getChildren().removeIf(child -> child instanceof ResizeHandles);
        } else {
            getPresentationView().getChildren().removeIf(child -> child instanceof ResizeHandles && ((ResizeHandles) child).getElementView() == elementView);
        }
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

    // selected element support

    private final ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>(this, "selectedElement");

    public final ObjectProperty<Element> selectedElementProperty() {
        return selectedElement;
    }

    public final void setSelectedElement(Element element) {
        this.selectedElement.set(element);
    }

    public final Element getSelectedElement() {
        return selectedElement.get();
    }

    // presentation view support

    private final ObjectProperty<PresentationView> presentationView = new SimpleObjectProperty<>(this, "presentationView");

    public final ObjectProperty<PresentationView> presentationViewProperty() {
        return presentationView;
    }

    public final void setPresentationView(PresentationView view) {
        this.presentationView.set(view);
    }

    public final PresentationView getPresentationView() {
        return presentationView.get();
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

        playIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLAY);
        playIcon.getStyleClass().add("play-button");

        pauseIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PAUSE);
        pauseIcon.getStyleClass().add("pause-button");

        playSlideButton = new Button();
        playSlideButton.setGraphic(playIcon);
        playSlideButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        playSlideButton.setOnAction(evt -> {
            if (playSlideButton.getGraphic().equals(pauseIcon)) {
                if (getPresentationView().getStatus().equals(Status.PAUSED)) {
                    playSlide();
                } else {
                    pauseSlide();
                }
            } else {
                playSlide();
            }
        });

        bar.getItems().add(playSlideButton);

        // stop slide
        Node stopIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.STOP);
        stopIcon.getStyleClass().add("stop-button");

        Button stopSlide = new Button();
        stopSlide.setGraphic(stopIcon);
        stopSlide.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        stopSlide.setOnAction(evt -> stop());
        bar.getItems().add(stopSlide);

        // reset slide
        Button resetSlide = new Button("Reset");
        resetSlide.setOnAction(evt -> reset());
        bar.getItems().add(resetSlide);

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
            Slide slide = new Slide();
            slide.setName(name);
            getPresentation().getSlides().add(slide);
            setSelectedSlide(slide);
        });
    }

    private void playSlide() {
        getPresentationView().setStatus(Status.PLAY);
    }

    private void pauseSlide() {
        getPresentationView().setStatus(Status.PAUSED);
    }

    private void stop() {
        getPresentationView().setStatus(Status.STOPPED);
    }

    private void reset() {
        getPresentationView().setStatus(Status.STOPPED);
        getPresentationView().getCurrentSlideView().reset();
    }

    private void updateSlide() {
        if (getPresentationView() != null) {
            Bindings.unbindBidirectional(getPresentationView().currentSlideProperty(), selectedSlideProperty());
            getPresentationView().destroy();
        }

        Project project = getProject();
        Presentation presentation = getPresentation();

        if (project == null || presentation == null) {
            return;
        }

        setPresentationView(new PresentationView(project, presentation));

        installHandlers(getPresentationView());

        //presentationView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getPresentation())));
        Bindings.bindBidirectional(getPresentationView().currentSlideProperty(), selectedSlideProperty());

        getPresentationView().statusProperty().addListener(it -> {
            switch (getPresentationView().getStatus()) {
                case PLAY:
                    playSlideButton.setGraphic(pauseIcon);
                    break;
                case PAUSED:
                    playSlideButton.setGraphic(pauseIcon);
                    break;
                case STOPPED:
                    playSlideButton.setGraphic(playIcon);
                    break;
            }
        });

        switch (presentation.getLayout()) {
            case FILL:
                BorderPane.setAlignment(getPresentationView(), Pos.CENTER);
                StackPane presentationWrapper = new StackPane();
                presentationWrapper.getStyleClass().add("presentation-wrapper");
                presentationWrapper.getChildren().add(getPresentationView());
                presentationMasterDetailPane.setMasterNode(presentationWrapper);
                break;
            case FIXED_HEIGHT:
            case FIXED_WIDTH:
            case FIXED_SIZE:
                StackPane content = new StackPane();
                content.getChildren().add(getPresentationView());
                StackPane.setAlignment(getPresentationView(), Pos.CENTER);
                getPresentationView().setEffect(new DropShadow(20, Color.BLACK));
                ScrollPane scrollPane = new ScrollPane(content);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);
                presentationMasterDetailPane.setMasterNode(scrollPane);
                break;
        }
    }

    private double mouseX, mouseY;

    private void installHandlers(PresentationView presentationView) {

        getPresentationView().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (evt.getClickCount() == 1 && evt.getButton().equals(MouseButton.PRIMARY)) {
                Object object = evt.getTarget();
                if (object instanceof ElementView) {
                    ElementView view = (ElementView) object;
                    initResize(view);
                } else if (!(object instanceof ResizeHandle) && !(object instanceof ResizeHandles)) {
                    stopResize();
                }
            }
        });

        getPresentationView().addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            if (evt.getClickCount() == 1 && evt.getButton().equals(MouseButton.PRIMARY)) {
                Object object = evt.getTarget();

                ElementView elementView = null;
                if (object instanceof ElementView) {
                    elementView = (ElementView) object;
                } else if (object instanceof ResizeHandles) {
                    elementView = ((ResizeHandles) object).getElementView();
                }

                if (elementView != null) {
                    setSelectedElement(elementView.getElement());
                    mouseX = evt.getSceneX();
                    mouseY = evt.getSceneY();
                } else if (!(object instanceof ResizeHandle) && !(object instanceof ResizeHandles)) {
                    stopResize();
                }
            }

            presentationView.requestFocus();
        });

        getPresentationView().addEventFilter(MouseEvent.MOUSE_DRAGGED, evt -> {
            Object object = evt.getTarget();

            ElementView elementView = null;
            if (object instanceof ElementView) {
                elementView = (ElementView) object;
            } else if (object instanceof ResizeHandles) {
                elementView = ((ResizeHandles) object).getElementView();
            }

            if (elementView != null) {
                if (elementView.getElement().getLayoutStrategy().equals(LayoutStrategy.ABSOLUTE)) {
                    double deltaX = evt.getSceneX() - mouseX;
                    double deltaY = evt.getSceneY() - mouseY;
                    elementView.relocate(elementView.getLayoutX() + deltaX, elementView.getLayoutY() + deltaY);
                    mouseX = evt.getSceneX();
                    mouseY = evt.getSceneY();
                }
            }
        });

        getPresentationView().addEventFilter(DragEvent.DRAG_OVER, evt -> {
            Dragboard db = evt.getDragboard();
            if (db.hasFiles()) {
                evt.acceptTransferModes(TransferMode.COPY);
            } else {
                evt.consume();
            }
        });

        getPresentationView().addEventHandler(DragEvent.DRAG_DROPPED, evt -> {
            Dragboard db = evt.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;

                final Project project = getProject();
                final Slide slide = getSelectedSlide();

                db.getFiles().forEach(file -> {
                    try {
                        final boolean image = isImage(file);
                        final boolean video = isVideo(file);

                        if (image || video) {
                            project.addFile(file);
                            String fileName = file.getName();
                            if (image) {
                                ImageElement imageElement = new ImageElement();
                                imageElement.setName(fileName);
                                imageElement.setImageFileName(fileName);
                                Image tempImage = new Image(project.getFile(fileName).toURI().toURL().toExternalForm());
                                imageElement.setWidth(tempImage.getWidth());
                                imageElement.setHeight(tempImage.getHeight());
                                slide.getElements().add(imageElement);
                            } else {
                                VideoElement videoElement = new VideoElement();
                                videoElement.setName(fileName);
                                videoElement.setVideoFileName(fileName);

                                // 16:9 aspect ratio is default
                                videoElement.setWidth(320);
                                videoElement.setHeight(180);

                                Play play = new Play();
                                play.setDuration(Duration.seconds(30));
                                videoElement.getOpeningTransitions().add(play);

                                slide.getElements().add(videoElement);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            evt.setDropCompleted(success);
            evt.consume();
        });

        getPresentationView().addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
            if (evt.getCode().equals(KeyCode.BACK_SPACE)) {
                Element element = getSelectedElement();
                if (element != null) {
                    // TODO: add confirmation dialog
                    getSelectedSlide().getElements().remove(element);
                }
            }
        });

        presentationView.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, evt -> {

            if (contextMenu != null && contextMenu.isShowing()) {
                contextMenu.hide();
            }

            Object object = evt.getTarget();

            ElementView elementView = null;
            if (object instanceof ElementView) {
                elementView = (ElementView) object;
            } else if (object instanceof ResizeHandles) {
                elementView = ((ResizeHandles) object).getElementView();
            }

            if (elementView != null) {

                final Element element = elementView.getElement();

                contextMenu = new ContextMenu();
                contextMenu.setAutoHide(true);
                contextMenu.setAutoFix(true);

                MenuItem toFront = new MenuItem("To Front");
                toFront.setOnAction(e -> {
                    getSelectedSlide().getElements().remove(element);
                    getSelectedSlide().getElements().add(element);
                });

                MenuItem toBack = new MenuItem("To Back");
                toBack.setOnAction(e -> {
                    getSelectedSlide().getElements().remove(element);
                    getSelectedSlide().getElements().add(0, element);
                });

                SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

                MenuItem forward = new MenuItem("Forward");
                forward.setOnAction(e -> {
                    int index = getSelectedSlide().getElements().indexOf(element);
                    getSelectedSlide().getElements().remove(element);
                    getSelectedSlide().getElements().add(index + 1, element);
                });

                MenuItem backward = new MenuItem("Backward");
                backward.setOnAction(e -> {
                    int index = getSelectedSlide().getElements().indexOf(element);
                    getSelectedSlide().getElements().remove(element);
                    getSelectedSlide().getElements().add(Math.max(0, index - 1), element);
                });

                contextMenu.getItems().addAll(toFront, toBack, separatorMenuItem, forward, backward);

                contextMenu.show(presentationView, evt.getScreenX(), evt.getScreenY());
            }
        });
    }

    private ContextMenu contextMenu;

    private boolean isImage(File file) {
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
        return suffix.equals(".png") || suffix.equals(".jpg") || suffix.equals(".gif");
    }

    private boolean isVideo(File file) {
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
        return suffix.equals(".mp4");
    }
}
