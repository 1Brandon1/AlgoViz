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

    public static PlaybackController getController() {

        return controller;
    }

    public static void load(
            List<AnimationEvent> newEvents,
            ArrayVisualiser newVisualiser) {

        stop();

        events = newEvents;

        visualiser = newVisualiser;

        controller.reset();

        visualiser.reset();
    }

    public static void stepForward() {

        if (events == null || visualiser == null) {
            return;
        }

        if (controller.getCurrentIndex() >= events.size()) {
            return;
        }

        applyEvent(
                events.get(controller.getCurrentIndex()));

        controller.next();
    }

    public static void stepBack() {

        if (events == null || visualiser == null) {
            return;
        }

        if (controller.getCurrentIndex() <= 0) {
            return;
        }

        controller.previous();

        rebuild();
    }

    private static void rebuild() {

        visualiser.reset();

        int target = controller.getCurrentIndex();

        for (int i = 0; i < target; i++) {

            applyEvent(
                    events.get(i));
        }
    }

    private static void applyEvent(
            AnimationEvent event) {

        if (event instanceof CompareEvent compare) {

            visualiser.compare(
                    compare.getFirstIndex(),
                    compare.getSecondIndex());

        } else if (event instanceof SwapEvent swap) {

            visualiser.swapBars(
                    swap.getFirstIndex(),
                    swap.getSecondIndex());

        } else if (event instanceof OverwriteEvent overwrite) {

            visualiser.setBarHeight(
                    overwrite.getIndex(),
                    overwrite.getNewValue());

        } else if (event instanceof PivotEvent pivot) {

            visualiser.pivot(
                    pivot.getPivotIndex());

        } else if (event instanceof SortedEvent sorted) {

            visualiser.markSorted(
                    sorted.getIndex());

        } else if (event instanceof SearchCompareEvent search) {

            visualiser.searchCompare(
                    search.getIndex());

        } else if (event instanceof FoundEvent found) {

            visualiser.markFound(
                    found.getIndex());

        } else if (event instanceof NotFoundEvent) {

            visualiser.markNotFound();
        } else if (event instanceof SortArrayEvent) {

            visualiser.sortBars();
        } else if (event instanceof SearchRangeEvent range) {

            visualiser.setSearchRange(
                    range.getLeft(),
                    range.getRight());
        }
    }

    public static void play() {

        if (events == null || visualiser == null) {
            return;
        }

        stop();

        controller.play();

        timeline = new Timeline(

                new KeyFrame(
                        Duration.millis(40),

                        e -> {

                            if (!controller.isPlaying()) {

                                stop();

                                return;
                            }

                            if (controller.getCurrentIndex() >= events.size()) {

                                stop();

                                return;
                            }

                            stepForward();
                        }));

        timeline.setCycleCount(
                Timeline.INDEFINITE);

        timeline.play();
    }

    public static void stop() {

        controller.pause();

        if (timeline != null) {

            timeline.stop();

            timeline = null;
        }
    }
}