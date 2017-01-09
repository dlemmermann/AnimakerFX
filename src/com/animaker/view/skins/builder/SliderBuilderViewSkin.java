package com.animaker.view.skins.builder;

import com.animaker.model.Layer;
import com.animaker.model.Slide;
import com.animaker.model.Slider;
import com.animaker.view.SliderView;
import com.animaker.view.builder.LayerSettingsView;
import com.animaker.view.builder.LayersPaletteView;
import com.animaker.view.builder.SliderBuilderView;
import com.animaker.view.builder.SlidesPaletteView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
public class SliderBuilderViewSkin extends SkinBase<SliderBuilderView> {

    private final PropertySheet propertySheet;
    private SlidesPaletteView slidesPaletteView;
    private LayersPaletteView layersPaletteView;
    private SliderView sliderView;
    private MasterDetailPane centerPane;

    public SliderBuilderViewSkin(SliderBuilderView view) {
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

        slidesPaletteView.sliderProperty().bind(view.sliderProperty());
        layersPaletteView.slideProperty().bind(slidesPaletteView.selectedSlideProperty());

        Bindings.bindBidirectional(slidesPaletteView.selectedSlideProperty(), view.selectedSlideProperty());
        Bindings.bindBidirectional(layersPaletteView.selectedLayerProperty(), view.selectedLayerProperty());


        view.selectedSlideProperty().addListener(it -> updateSlide());

        view.setPrefSize(1500, 800);

        view.selectedSlideProperty().addListener(it -> propertySheet.getItems().
                setAll(BeanPropertyUtils.getProperties(view.getSelectedSlide())));

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
        save.setOnAction(evt -> saveSlider());
        bar.getItems().add(save);

        // load
        Button load = new Button("Load");
        load.setOnAction(evt -> loadSlider());
        bar.getItems().add(load);

        // add slide
        Button addSlide = new Button("Add Slide");
        addSlide.setOnAction(evt -> addSlide());
        bar.getItems().add(addSlide);

        return bar;
    }

    private void saveSlider() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Slider.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(getSkinnable().getSlider(), new File(System.getProperty("user.home"), "SliderFX.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void loadSlider() {
        try {
            JAXBContext ctx = JAXBContext.newInstance(Slider.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Slider slider = (Slider) unmarshaller.unmarshal(new File(System.getProperty("user.home"), "SliderFX.xml"));
            getSkinnable().setSlider(slider);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void addSlide() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Slide");
        dialog.setHeaderText("Add a new slide to the current slider.");
        dialog.setContentText("Slider Name:");
        dialog.showAndWait().ifPresent(name -> {
            Slide slide = new Slide(name);
            getSkinnable().getSlider().getSlides().add(slide);
            getSkinnable().setSelectedSlide(slide);
        });
    }

    private void updateSlide() {
        if (sliderView != null) {
            Bindings.unbindBidirectional(sliderView.currentSlideProperty(), getSkinnable().selectedSlideProperty());
        }

        Slider slider = getSkinnable().getSlider();
        sliderView = new SliderView(slider);
        sliderView.addEventFilter(MouseEvent.MOUSE_CLICKED,
                evt -> propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(getSkinnable().getSlider())));
        Bindings.bindBidirectional(sliderView.currentSlideProperty(), getSkinnable().selectedSlideProperty());

        switch (slider.getLayout()) {
            case FILL:
                BorderPane.setAlignment(sliderView, Pos.CENTER);
                centerPane.setMasterNode(sliderView);
                break;
            case FIXED_HEIGHT:
            case FIXED_WIDTH:
            case FIXED_SIZE:
                StackPane content = new StackPane();
                content.getChildren().add(sliderView);
                StackPane.setAlignment(sliderView, Pos.CENTER);
                sliderView.setEffect(new DropShadow(20, Color.BLACK));
                ScrollPane scrollPane = new ScrollPane(content);
                scrollPane.setFitToHeight(true);
                scrollPane.setFitToWidth(true);
                centerPane.setMasterNode(scrollPane);
                break;
        }
    }
}
