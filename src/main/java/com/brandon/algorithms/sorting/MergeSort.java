package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.*;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements SortingAlgorithm {

    @Override
    public List<AnimationEvent> generateEvents(int[] array) {

        List<AnimationEvent> events = new ArrayList<>();
        int[] copy = array.clone();

        mergeSort(copy, 0, copy.length - 1, events);

        return events;
    }

    private void mergeSort(int[] arr, int left, int right,
                           List<AnimationEvent> events) {

        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid, events);
        mergeSort(arr, mid + 1, right, events);

        merge(arr, left, mid, right, events);
    }

    private void merge(int[] arr, int left, int mid, int right,
                       List<AnimationEvent> events) {

        int[] temp = new int[right - left + 1];

        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {

            events.add(new CompareEvent(i, j));

            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int p = 0; p < temp.length; p++) {

            arr[left + p] = temp[p];

            events.add(new OverwriteEvent(left + p, temp[p]));
        }
    }
}