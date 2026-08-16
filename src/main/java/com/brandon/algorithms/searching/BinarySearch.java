package com.brandon.algorithms.searching;

import com.brandon.algorithms.SearchingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.FoundEvent;
import com.brandon.events.NotFoundEvent;
import com.brandon.events.SearchCompareEvent;
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

        int[] sorted = Arrays.copyOf(
                array,
                array.length);

        Arrays.sort(sorted);

        // Sort the visualiser in one event
        events.add(new SortArrayEvent());

        int left = 0;
        int right = sorted.length - 1;

        while (left <= right) {

            int middle = left + (right - left) / 2;

            events.add(
                    new SearchCompareEvent(
                            middle));

            if (sorted[middle] == target) {

                events.add(
                        new FoundEvent(
                                middle));

                return events;
            }

            if (sorted[middle] < target) {

                left = middle + 1;

            } else {

                right = middle - 1;
            }
        }

        events.add(
                new NotFoundEvent());

        return events;
    }
}