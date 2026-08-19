package com.brandon.algorithms;

import com.brandon.events.AnimationEvent;

import java.util.List;

public interface SearchingAlgorithm {

    List<AnimationEvent> generateEvents(int[] array, int target);
}