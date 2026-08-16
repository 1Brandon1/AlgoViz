package com.brandon.algorithms.searching;

import com.brandon.algorithms.SearchingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.FoundEvent;
import com.brandon.events.NotFoundEvent;
import com.brandon.events.SearchCompareEvent;
import com.brandon.events.SearchRangeEvent;
import com.brandon.events.SortArrayEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearch implements SearchingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(
            int[] array,
            int target) {

        List<AnimationEvent> events = new ArrayList<>();

        // ---------------------------------------------
        // SORT ARRAY
        // ---------------------------------------------

        int[] sorted = Arrays.copyOf(
                array,
                array.length);

        Arrays.sort(sorted);

        events.add(
                new SortArrayEvent());

        // ---------------------------------------------
        // BINARY SEARCH
        // ---------------------------------------------

        int left = 0;
        int right = sorted.length - 1;

        while (left <= right) {

            // Show the current search range
            events.add(
                    new SearchRangeEvent(
                            left,
                            right));

            int middle = left + (right - left) / 2;

            // Highlight midpoint
            events.add(
                    new SearchCompareEvent(
                            middle));

            // Target found
            if (sorted[middle] == target) {

                events.add(
                        new FoundEvent(
                                middle));

                return events;
            }

            // Target is larger
            if (sorted[middle] < target) {

                left = middle + 1;

            }
            // Target is smaller
            else {

                right = middle - 1;
            }
        }

        // Target does not exist
        events.add(
                new NotFoundEvent());

        return events;
    }
}