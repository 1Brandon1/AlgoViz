package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SortCompleteEvent;
import com.brandon.events.SortedEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();

        int[] copy = array.clone();

        for (int i = 0; i < copy.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < copy.length; j++) {

                events.add(new CompareEvent(minIndex, j));

                if (copy[j] < copy[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {

                events.add(new SwapEvent(i, minIndex));

                int temp = copy[i];
                copy[i] = copy[minIndex];
                copy[minIndex] = temp;
            }

            events.add(new SortedEvent(i));
        }

        events.add(new SortedEvent(copy.length - 1));
        events.add(new SortCompleteEvent());

        return events;
    }
}