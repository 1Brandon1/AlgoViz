package com.brandon.algorithms;

import com.brandon.algorithms.sorting.BubbleSort;
import com.brandon.algorithms.sorting.SelectionSort;
import com.brandon.algorithms.sorting.InsertionSort;
import com.brandon.algorithms.sorting.MergeSort;

public class AlgorithmFactory {

    public static SortingAlgorithm create(AlgorithmType type) {

        return switch (type) {

            case BUBBLE_SORT -> new BubbleSort();
            case SELECTION_SORT -> new SelectionSort();
            case INSERTION_SORT -> new InsertionSort();
            case MERGE_SORT -> new MergeSort();
        };
    }
}