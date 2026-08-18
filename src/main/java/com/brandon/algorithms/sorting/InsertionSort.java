package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SortCompleteEvent;
import com.brandon.events.SortedEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();

        int[] copy = array.clone();

        for (int i = 1; i < copy.length; i++) {

            int j = i;

            while (j > 0 && copy[j] < copy[j - 1]) {

                events.add(new CompareEvent(j, j - 1));
                events.add(new SwapEvent(j, j - 1));

                int temp = copy[j];
                copy[j] = copy[j - 1];
                copy[j - 1] = temp;

                j--;
            }

            for (int k = 0; k <= i; k++) {
                events.add(new SortedEvent(k));
            }
        }
        events.add(new SortCompleteEvent());

        return events;
    }
}