package com.brandon.algorithms;

import com.brandon.events.AnimationEvent;

import java.util.List;

public class SearchEngine {

    public static List<AnimationEvent> run(
            SearchType type,
            int[] array,
            int target) {

        SearchingAlgorithm algorithm = SearchFactory.create(type);

        return algorithm.generateEvents(array, target);
    }
}