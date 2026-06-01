package com.brandon.visualisation;

import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SwapEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class EventPlayer {

    public static void play(
            List<AnimationEvent> events,
            ArrayVisualiser visualiser
    ) {

        Timeline timeline = new Timeline();

        for (int i = 0; i < events.size(); i++) {

            AnimationEvent event = events.get(i);

            KeyFrame frame = new KeyFrame(
                    Duration.millis(i * 20),
                    e -> {

                        if (event instanceof CompareEvent compare) {

                            visualiser.highlightBars(
                                    compare.getFirstIndex(),
                                    compare.getSecondIndex()
                            );
                        }
                        else if (event instanceof SwapEvent swap) {

                            visualiser.swapBars(
                                    swap.getFirstIndex(),
                                    swap.getSecondIndex()
                            );
                        }
                    }
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
        timeline.setOnFinished(e -> visualiser.resetColors());
    }
}