package com.brandon.algorithms;

import com.brandon.algorithms.searching.BinarySearch;
import com.brandon.algorithms.searching.JumpSearch;
import com.brandon.algorithms.searching.LinearSearch;
import com.brandon.algorithms.searching.InterpolationSearch;

public class SearchFactory {

    public static SearchingAlgorithm create(
            SearchType type) {

        return switch (type) {

            case LINEAR_SEARCH -> new LinearSearch();
            case BINARY_SEARCH -> new BinarySearch();
            case JUMP_SEARCH -> new JumpSearch();
            case INTERPOLATION_SEARCH -> new InterpolationSearch();
        };
    }
}