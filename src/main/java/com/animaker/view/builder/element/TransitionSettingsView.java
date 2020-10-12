package com.animaker.view.builder.element;

import java.util.Objects;

import com.animaker.model.Element;
import com.animaker.model.transition.Blur;
import com.animaker.model.transition.Drift;
import com.animaker.model.transition.FadeIn;
import com.animaker.model.transition.FadeOut;
import com.animaker.model.transition.MoveIn;
import com.animaker.model.transition.MoveOut;
import com.animaker.model.transition.Parallax;
import com.animaker.model.transition.Play;
import com.animaker.model.transition.Rotate;
import com.animaker.model.transition.Scale;
import com.animaker.model.transition.Transition;
import com.animaker.model.transition.Transition.TransitionType;
import com.animaker.view.builder.Workbench;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by lemmi on 20.12.16.
 */
public class TransitionSettingsView extends ElementSettingsView<Element> {

    public enum TransitionLocationType {
        OPENING,
        SHOWING,
        CLOSING,
        PARALLAX,
        HOVER;
    }

    private TransitionLocationType type;

    private ListView<Transition> listView;

    public TransitionSettingsView(Workbench workbench, String title, TransitionLocationType type) {
        super(workbench);

        this.type = Objects.requireNonNull(type);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("palette-title");
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        listView = new ListView<>();

        MenuButton addTransitionButton = new MenuButton("Add");
        for (TransitionType tt : TransitionType.values()) {
            MenuItem item = new MenuItem(tt.toString());
            item.setOnAction(evt -> addTransition(tt));
            addTransitionButton.getItems().add(item);
        }

        Button removeTransitionButton = new Button("Remove");
        removeTransitionButton.setOnAction(evt -> removeTransition());

        HBox buttonBar = new HBox(addTransitionButton, removeTransitionButton);
        buttonBar.getStyleClass().add("button-bar");

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleLabel);
        borderPane.setCenter(listView);
        borderPane.setBottom(buttonBar);

        getChildren().add(borderPane);
    }

    private void addTransition(TransitionType type) {
        switch (type) {
            case BLUR:
                listView.getItems().add(new Blur());
                break;
            case DRIFT:
                listView.getItems().add(new Drift());
                break;
            case FADE_IN:
                listView.getItems().add(new FadeIn());
                break;
            case FADE_OUT:
                listView.getItems().add(new FadeOut());
                break;
            case MOVE_IN:
                listView.getItems().add(new MoveIn());
                break;
            case MOVE_OUT:
                listView.getItems().add(new MoveOut());
                break;
            case PARALLAX:
                listView.getItems().add(new Parallax());
                break;
            case PLAY:
                listView.getItems().add(new Play());
                break;
            case ROTATE:
                listView.getItems().add(new Rotate());
                break;
            case SCALE:
                listView.getItems().add(new Scale());
                break;
        }
    }

    private void removeTransition() {
        listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
    }

    @Override
    protected void update(Element oldElement, Element newElement) {
        if (newElement != null) {
            switch (type) {
                case OPENING:
                    listView.setItems(newElement.getOpeningTransitions());
                    break;
                case CLOSING:
                    listView.setItems(newElement.getClosingTransitions());
                    break;
                case SHOWING:
                    listView.setItems(newElement.getLoopTransitions());
                    break;
                case PARALLAX:
                    break;
                case HOVER:
                    listView.setItems(newElement.getHoverTransitions());
                    break;
            }
        } else {
            listView.setItems(FXCollections.emptyObservableList());
        }
    }
}
