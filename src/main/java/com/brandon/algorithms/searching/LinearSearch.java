package com.brandon.algorithms.searching;

import com.brandon.algorithms.SearchingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.FoundEvent;
import com.brandon.events.SearchCompareEvent;

import java.util.ArrayList;
import java.util.List;

public class LinearSearch implements SearchingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(
            int[] array,
            int target) {

        List<AnimationEvent> events = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {

            events.add(
                    new SearchCompareEvent(i));

            if (array[i] == target) {

                events.add(
                        new FoundEvent(i));

                break;
            }
        }

        return events;
    }
}