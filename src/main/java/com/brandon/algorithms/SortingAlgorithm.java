package com.brandon.algorithms;

import com.brandon.events.AnimationEvent;
import java.util.List;

public interface SortingAlgorithm {

    List<AnimationEvent> generateEvents(int[] array);
}