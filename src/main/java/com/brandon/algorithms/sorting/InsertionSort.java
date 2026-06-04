package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();

        for (int i = 1; i < array.length; i++) {

            int j = i;

            while (j > 0 && array[j] < array[j - 1]) {

                events.add(new CompareEvent(j, j - 1));
                events.add(new SwapEvent(j, j - 1));

                int temp = array[j];
                array[j] = array[j - 1];
                array[j - 1] = temp;

                j--;
            }
        }

        return events;
    }
}