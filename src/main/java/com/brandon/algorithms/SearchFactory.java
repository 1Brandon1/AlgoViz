package com.brandon.algorithms;

import com.brandon.algorithms.searching.BinarySearch;
import com.brandon.algorithms.searching.LinearSearch;

public class SearchFactory {

    public static SearchingAlgorithm create(
            SearchType type) {

        return switch (type) {

            case LINEAR_SEARCH -> new LinearSearch();
            case BINARY_SEARCH -> new BinarySearch();
        };
    }
}