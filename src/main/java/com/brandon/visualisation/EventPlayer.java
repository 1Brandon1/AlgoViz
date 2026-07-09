package com.brandon.visualisation;

import com.brandon.events.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class EventPlayer {

    private static final PlaybackController controller = new PlaybackController();

    private static Timeline timeline;

    public static PlaybackController getController() {
        return controller;
    }

    public static void load(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        stop();

        controller.reset();

        visualiser.reset();
    }

    public static void stop() {

        controller.pause();

        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    public static void stepForward(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        if (events == null) {
            return;
        }

        if (controller.getCurrentIndex() >= events.size()) {
            return;
        }

        applyEvent(
                events.get(controller.getCurrentIndex()),
                visualiser);

        controller.next();
    }

    public static void stepBack(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        if (events == null) {
            return;
        }

        if (controller.getCurrentIndex() <= 0) {
            return;
        }

        controller.previous();

        rebuild(
                events,
                visualiser);
    }

    public static void rebuild(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        visualiser.reset();

        int target = controller.getCurrentIndex();

        for (int i = 0; i < target; i++) {

            applyEvent(
                    events.get(i),
                    visualiser);
        }
    }

    private static void applyEvent(
            AnimationEvent event,
            ArrayVisualiser visualiser) {

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
        }
    }

    public static void play(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        if (events == null || events.isEmpty()) {
            return;
        }

        // Stop any previous playback
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

                            stepForward(events, visualiser);
                        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}