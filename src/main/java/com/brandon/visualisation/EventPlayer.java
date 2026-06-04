package com.brandon.visualisation;

import com.brandon.events.*;
import javafx.application.Platform;

import java.util.List;

public class EventPlayer {

    private static final int DELAY_MS = 20;

    public static void play(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser
    ) {

        playNext(events, visualiser, 0);
    }

    private static void playNext(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser,
            int index
    ) {

        if (index >= events.size()) {
            visualiser.resetColors();
            return;
        }

        AnimationEvent event = events.get(index);

        Runnable next = () ->
                playNext(events, visualiser, index + 1);

        if (event instanceof CompareEvent compare) {

            visualiser.highlightBars(
                    compare.getFirstIndex(),
                    compare.getSecondIndex(),
                    next
            );

        } else if (event instanceof SwapEvent swap) {

            visualiser.swapBars(
                    swap.getFirstIndex(),
                    swap.getSecondIndex(),
                    next
            );

        } else {

            next.run();
        }
    }
}