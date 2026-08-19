package com.brandon.visualisation;

import com.brandon.events.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class EventPlayer {

    private static final PlaybackController controller = new PlaybackController();

    private static List<AnimationEvent> events;
    private static ArrayVisualiser visualiser;
    private static Timeline timeline;
    private static Runnable updateListener;

    // =================================================
    // CONTROLLER
    // =================================================

    public static PlaybackController getController() {
        return controller;
    }

    // =================================================
    // UPDATE LISTENER
    // =================================================

    public static void setUpdateListener(
            Runnable listener) {

        updateListener = listener;
    }

    private static void notifyUpdate() {

        if (updateListener != null) {
            updateListener.run();
        }
    }

    // =================================================
    // LOAD
    // =================================================

    public static void load(
            List<AnimationEvent> newEvents,
            ArrayVisualiser newVisualiser) {

        stop();

        events = newEvents;
        visualiser = newVisualiser;
        controller.reset();

        if (visualiser != null) {
            visualiser.reset();
        }

        notifyUpdate();
    }

    // =================================================
    // STEP FORWARD
    // =================================================

    public static void stepForward() {

        if (events == null || visualiser == null) {
            return;
        }

        if (controller.getCurrentIndex() >= events.size()) {
            return;
        }

        AnimationEvent event = events.get(controller.getCurrentIndex());

        applyEvent(event, visualiser);
        controller.next();
        notifyUpdate();
    }

    // =================================================
    // STEP BACK
    // =================================================

    public static void stepBack() {

        if (events == null ||
                visualiser == null) {
            return;
        }

        if (controller.getCurrentIndex() <= 0) {
            return;
        }

        controller.previous();
        rebuild();
        notifyUpdate();
    }

    // =================================================
    // REBUILD
    // =================================================

    private static void rebuild() {

        visualiser.reset();
        int target = controller.getCurrentIndex();

        for (int i = 0; i < target; i++) {

            applyEvent(events.get(i), visualiser);
        }
    }

    // =================================================
    // PLAY
    // =================================================

    public static void play() {

        if (events == null || visualiser == null || events.isEmpty()) {
            return;
        }

        if (controller.getCurrentIndex() >= events.size()) {

            return;
        }

        controller.play();

        timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(40),
                        e -> playNext()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // =================================================
    // PLAY NEXT
    // =================================================

    private static void playNext() {

        if (!controller.isPlaying()) {
            return;
        }

        if (events == null || controller.getCurrentIndex() >= events.size()) {
            stop();
            return;
        }

        stepForward();
    }

    // =================================================
    // STOP
    // =================================================

    public static void stop() {

        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }

        controller.pause();
        notifyUpdate();
    }

    // =================================================
    // APPLY EVENT
    // =================================================

    private static void applyEvent(
            AnimationEvent event,
            ArrayVisualiser visualiser) {

        if (event instanceof CompareEvent compare) {

            visualiser.compare(
                    compare.getFirstIndex(),
                    compare.getSecondIndex());

        } else if (event instanceof SearchCompareEvent searchCompare) {

            visualiser.searchCompare(searchCompare.getIndex());

        } else if (event instanceof SwapEvent swap) {

            visualiser.swapBars(
                    swap.getFirstIndex(),
                    swap.getSecondIndex());

        } else if (event instanceof OverwriteEvent overwrite) {

            visualiser.setBarHeight(
                    overwrite.getIndex(),
                    overwrite.getNewValue());

        } else if (event instanceof PivotEvent pivot) {

            visualiser.pivot(pivot.getPivotIndex());

        } else if (event instanceof SortedEvent sorted) {

            visualiser.markSorted(sorted.getIndex());

        } else if (event instanceof SearchCompareEvent search) {

            visualiser.searchCompare(search.getIndex());

        } else if (event instanceof FoundEvent found) {

            visualiser.markFound(found.getIndex());

        } else if (event instanceof NotFoundEvent) {

            visualiser.markNotFound();

        } else if (event instanceof SortArrayEvent) {

            visualiser.sortBars();

        } else if (event instanceof SearchRangeEvent range) {

            visualiser.setSearchRange(
                    range.getLeft(),
                    range.getRight());

        } else if (event instanceof SortCompleteEvent) {

            visualiser.markSortComplete();
        }
    }
}