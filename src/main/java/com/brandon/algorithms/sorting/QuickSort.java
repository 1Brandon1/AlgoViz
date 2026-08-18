package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SortCompleteEvent;
import com.brandon.events.SortedEvent;
import com.brandon.events.SwapEvent;

import java.util.ArrayList;
import java.util.List;

public class QuickSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();
        int[] copy = array.clone();

        quickSort(copy, 0, copy.length - 1, events);

        events.add(new SortCompleteEvent());
        return events;
    }

    private void quickSort(int[] arr, int low, int high, List<AnimationEvent> events) {

        if (low < high) {

            int pivotIndex = partition(arr, low, high, events);

            events.add(new SortedEvent(pivotIndex));

            quickSort(arr, low, pivotIndex - 1, events);
            quickSort(arr, pivotIndex + 1, high, events);
        }
    }

    private int partition(int[] arr, int low, int high, List<AnimationEvent> events) {

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {

            events.add(new CompareEvent(j, high));

            if (arr[j] < pivot) {

                i++;

                if (i != j) {

                    events.add(new SwapEvent(i, j));
                    swap(arr, i, j);
                }
            }
        }

        if (i + 1 != high) {

            events.add(new SwapEvent(i + 1, high));
            swap(arr, i + 1, high);
        }

        return i + 1;
    }

    private void swap(int[] arr, int a, int b) {

        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}