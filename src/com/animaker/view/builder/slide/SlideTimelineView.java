package com.animaker.view.builder.slide;

import com.animaker.model.Element;
import com.animaker.model.ModelObject;
import com.animaker.model.Slide;
import com.animaker.model.transition.Transition;
import com.animaker.view.PresentationView;
import com.animaker.view.SlideView;
import com.animaker.view.builder.Workbench;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lemmi on 10.03.17.
 */
public class SlideTimelineView extends SlideControlBase {

    private TreeTableView<Slide> treeTableView;

    private Duration visibleDuration = Duration.seconds(5);

    public SlideTimelineView(Workbench workbench) {
        super(workbench);

        TreeTableColumn<Slide, String> nameColumn = new TreeTableColumn<>("Item");
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        TreeTableColumn<Slide, ModelObject> timelineColumn = new TreeTableColumn<>("Timeline");
        timelineColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("self"));
        timelineColumn.setCellFactory(column -> new TimelineCell());
        timelineColumn.setPrefWidth(900);

        treeTableView = new TreeTableView<>();
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().setAll(nameColumn, timelineColumn);
        getChildren().add(treeTableView);

        workbench.presentationViewProperty().addListener(it -> listenToPresentationView());

        timelineProperty().addListener(it -> currentTime.bind(getTimeline().currentTimeProperty()));
    }

    private final InvalidationListener slideViewChangedListener = it -> slideChanged();

    private final WeakInvalidationListener weakSlideViewChangedListener = new WeakInvalidationListener(slideViewChangedListener);

    private void listenToPresentationView() {
        Workbench workbench = getWorkbench();
        PresentationView presentationView = workbench.getPresentationView();
        presentationView.currentSlideViewProperty().addListener(weakSlideViewChangedListener);
    }

    private void slideChanged() {
        Workbench workbench = getWorkbench();
        PresentationView presentationView = workbench.getPresentationView();
        SlideView slideView = presentationView.getCurrentSlideView();
        setTimeline(slideView.getTimeline());
    }

    // timeline support

    private final ObjectProperty<Timeline> timeline = new SimpleObjectProperty<>(this, "timeline");

    public final ObjectProperty<Timeline> timelineProperty() {
        return timeline;
    }

    public final void setTimeline(Timeline timeline) {
        this.timeline.set(timeline);
    }

    public final Timeline getTimeline() {
        return timeline.get();
    }

    private Duration getVisibleDuration() {
        return visibleDuration;
    }

    // duration support

    private final ObjectProperty<Duration> currentTime = new SimpleObjectProperty<>(this, "duration");

    @Override
    protected void updateSlide(Slide oldSlide, Slide newSlide) {
        treeTableView.setRoot(new SlideItem(newSlide));
    }

    class TimelineCell extends TreeTableCell<Slide, ModelObject> {

        private TransitionPane transitionPane;

        public TimelineCell() {
            transitionPane = new TransitionPane();
            setGraphic(transitionPane);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        protected void updateItem(ModelObject item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null && item instanceof Transition) {
                Transition transition = (Transition) item;
                transitionPane.setTransition(transition);
            } else {
                transitionPane.setTransition(null);
            }
        }
    }

    class TransitionPane extends Pane {

        private Region timelineBar = new Region();
        private Line currentTimeIndicator = new Line();
        private double mouseX;

        public TransitionPane() {
            timelineBar.setManaged(false);
            timelineBar.getStyleClass().add("transition-line");
            timelineBar.setOnMousePressed(evt -> mouseX = evt.getSceneX());
            timelineBar.setOnMouseDragged(evt -> {
                double delta = evt.getSceneX() - mouseX;
                final double newX = timelineBar.getLayoutX() + delta;
                if (timelineBar.getCursor().equals(Cursor.MOVE)) {
                    Duration duration = getDuration(newX, true);
                    transition.get().setDelay(duration);
                } else {
                    Duration duration = getDuration(delta, false);
                    Duration newDuration = transition.get().getDuration().add(duration);
                    if (newDuration.lessThan(Duration.ZERO)) {
                        newDuration = Duration.ZERO;
                    }
                    transition.get().setDuration(newDuration);
                }
                mouseX = evt.getSceneX();
            });

            timelineBar.setOnMouseMoved(evt -> {
                if (evt.getX() > timelineBar.getWidth() - 5) {
                    timelineBar.setCursor(Cursor.E_RESIZE);
                } else {
                    timelineBar.setCursor(Cursor.MOVE);
                }
            });

            currentTimeIndicator.setManaged(false);
            currentTimeIndicator.getStyleClass().add("current-time-indicator");
            currentTimeIndicator.setOnMousePressed(evt -> mouseX = evt.getSceneX());
            currentTimeIndicator.setOnMouseDragged(evt -> {
                double delta = evt.getSceneX() - mouseX;
                final double newX = currentTimeIndicator.getStartX() + delta;
                currentTimeIndicator.setStartX(newX);
                currentTimeIndicator.setEndX(newX);
                mouseX = evt.getSceneX();

                Workbench workbench = getWorkbench();
                PresentationView presentationView = workbench.getPresentationView();
                SlideView slideView = presentationView.getCurrentSlideView();
                slideView.jumpTo(getDuration(newX, true));
            });

            getChildren().addAll(timelineBar, currentTimeIndicator);

            transition.addListener((observable, oldTransition, newTransition) -> updateTransition(oldTransition, newTransition));

            currentTime.addListener(it -> requestLayout());
        }

        private Duration getDuration(double x, boolean safe) {
            Duration duration = getVisibleDuration();
            double mpp = duration.toMillis() / getWidth();
            return Duration.millis(safe ? Math.max(0, x * mpp) : x * mpp);
        }

        private final InvalidationListener redrawListener = it -> requestLayout();

        private final WeakInvalidationListener weakRedrawListener = new WeakInvalidationListener(redrawListener);

        private void updateTransition(Transition oldTransition, Transition newTransition) {

            if (oldTransition != null) {
                oldTransition.delayProperty().removeListener(weakRedrawListener);
                oldTransition.durationProperty().removeListener(weakRedrawListener);
            }

            if (newTransition != null) {
                newTransition.delayProperty().addListener(weakRedrawListener);
                newTransition.durationProperty().addListener(weakRedrawListener);
            }

            requestLayout();
        }

        private final ObjectProperty<Transition> transition = new SimpleObjectProperty<>(this, "transition");

        public final void setTransition(Transition t) {
            transition.set(t);
        }

        @Override
        protected void layoutChildren() {
            Transition t = transition.get();

            double w = getWidth();
            double h = getHeight();

            Duration duration = getVisibleDuration();
            double mpp = duration.toMillis() / w;

            if (t != null) {
                double x1 = t.getDelay().toMillis() / mpp;
                double x2 = x1 + t.getDuration().toMillis() / mpp;
                timelineBar.resizeRelocate(x1, 0, x2 - x1, h);
                timelineBar.setVisible(true);
            } else {
                timelineBar.setVisible(false);
            }

            double timeX = 0;

            Duration time = currentTime.get();
            if (time != null) {
                timeX = time.toMillis() / mpp;
                currentTimeIndicator.setStartX(timeX);
                currentTimeIndicator.setStartY(-4);
                currentTimeIndicator.setEndX(timeX);
                currentTimeIndicator.setEndY(h + 4);
            }
        }
    }

    public abstract class ModelObjectTreeItem<T extends ModelObject> extends TreeItem<T> {

        public ModelObjectTreeItem(T modelObject) {
            super(modelObject);
            setExpanded(true);
        }
    }

    public class SlideItem extends ModelObjectTreeItem<Slide> {

        public SlideItem(Slide slide) {
            super(slide);

            getValue().getElements().addListener((Observable it) -> updateChildren());

            updateChildren();
        }

        private void updateChildren() {
            List children = new ArrayList<>();
            getValue().getElements().forEach(transition -> children.add(new ElementItem(transition)));
            getChildren().setAll(children);
        }
    }

    public class ElementItem extends ModelObjectTreeItem<Element> {

        public ElementItem(Element element) {
            super(element);

            updateChildren();
            getValue().getOpeningTransitions().addListener((Observable it) -> updateChildren());
        }

        private void updateChildren() {
            List children = new ArrayList<>();
            getValue().getOpeningTransitions().forEach(transition -> children.add(new TransitionItem(transition)));
            getChildren().setAll(children);
        }
    }

    public class TransitionItem extends ModelObjectTreeItem<Transition> {

        public TransitionItem(Transition transition) {
            super(transition);
        }
    }
}
