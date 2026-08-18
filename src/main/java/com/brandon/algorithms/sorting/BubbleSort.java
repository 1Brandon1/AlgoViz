package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SortCompleteEvent;
import com.brandon.events.SortedEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();

        int[] copy = array.clone();

        for (int i = 0; i < copy.length - 1; i++) {

            for (int j = 0; j < copy.length - i - 1; j++) {

                events.add(new CompareEvent(j, j + 1));

                if (copy[j] > copy[j + 1]) {

                    events.add(new SwapEvent(j, j + 1));

                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                }
            }

            events.add(new SortedEvent(copy.length - 1 - i));
        }

        events.add(new SortedEvent(0));
        events.add(new SortCompleteEvent());

        return events;
    }
}