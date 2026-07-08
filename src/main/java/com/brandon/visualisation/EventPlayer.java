package com.brandon.visualisation;

import com.brandon.events.*;

import java.util.List;

public class EventPlayer {

    private static final PlaybackController controller = new PlaybackController();

    public static PlaybackController getController() {

        return controller;
    }

    public static void load(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

        controller.reset();

        visualiser.reset();
    }

    public static void stepForward(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser) {

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
}