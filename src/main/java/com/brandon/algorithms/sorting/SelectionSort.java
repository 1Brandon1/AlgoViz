package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();

        for (int i = 0; i < array.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {

                events.add(new CompareEvent(minIndex, j));

                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {

                events.add(new SwapEvent(i, minIndex));

                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        return events;
    }
}