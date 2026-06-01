package com.brandon.visualization;

import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;

public class EventPlayer {

    public static void play(
            List<AnimationEvent> events,
            ArrayVisualizer visualizer
    ) {

        Timeline timeline = new Timeline();

        for (int i = 0; i < events.size(); i++) {

            AnimationEvent event = events.get(i);

            KeyFrame frame = new KeyFrame(
                    Duration.millis(i * 20),
                    e -> {

                        if (event instanceof CompareEvent compare) {

                            visualizer.highlightBars(
                                    compare.getFirstIndex(),
                                    compare.getSecondIndex()
                            );
                        }
                    }
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }
}