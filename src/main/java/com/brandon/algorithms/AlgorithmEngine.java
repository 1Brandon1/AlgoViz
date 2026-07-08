package com.brandon.algorithms;

import com.brandon.events.AnimationEvent;

import java.util.List;

public class AlgorithmEngine {

    public static List<AnimationEvent> run(
            AlgorithmType type,
            int[] array) {

        SortingAlgorithm algorithm = AlgorithmFactory.create(type);

        return algorithm.generateEvents(array);
    }
}