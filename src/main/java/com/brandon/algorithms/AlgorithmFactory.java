package com.brandon.algorithms;

import com.brandon.algorithms.sorting.BubbleSort;

public class AlgorithmFactory {

    public static SortingAlgorithm create(AlgorithmType type) {

        return switch (type) {

            case BUBBLE_SORT -> new BubbleSort();
        };
    }
}