package com.brandon.algorithms.sorting;

import com.brandon.algorithms.SortingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SortCompleteEvent;
import com.brandon.events.SwapEvent;
import com.brandon.events.SortedEvent;

import java.util.ArrayList;
import java.util.List;

public class HeapSort implements SortingAlgorithm {

        @Override
        public List<AnimationEvent> generateEvents(
                        int[] array) {

                List<AnimationEvent> events = new ArrayList<>();

                int n = array.length;

                // ---------------------------------------------
                // BUILD MAX HEAP
                // ---------------------------------------------

                for (int i = n / 2 - 1; i >= 0; i--) {

                        heapify(
                                        array,
                                        n,
                                        i,
                                        events);
                }

                // ---------------------------------------------
                // EXTRACT ELEMENTS FROM HEAP
                // ---------------------------------------------

                for (int end = n - 1; end > 0; end--) {

                        events.add(
                                        new CompareEvent(
                                                        0,
                                                        end));

                        // Move largest element to the end
                        swap(
                                        array,
                                        0,
                                        end);

                        events.add(
                                        new SwapEvent(
                                                        0,
                                                        end));

                        events.add(
                                        new SortedEvent(
                                                        end));

                        // Restore heap
                        heapify(
                                        array,
                                        end,
                                        0,
                                        events);
                }

                // ---------------------------------------------
                // SINGLE ELEMENT
                // ---------------------------------------------

                if (n > 0) {

                        events.add(
                                        new SortedEvent(0));

                }

                events.add(new SortCompleteEvent());
                return events;
        }

        private void heapify(
                        int[] array,
                        int heapSize,
                        int root,
                        List<AnimationEvent> events) {

                int largest = root;

                int left = 2 * root + 1;

                int right = 2 * root + 2;

                // ---------------------------------------------
                // LEFT CHILD
                // ---------------------------------------------

                if (left < heapSize) {

                        events.add(
                                        new CompareEvent(
                                                        root,
                                                        left));

                        if (array[left] > array[largest]) {

                                largest = left;
                        }
                }

                // ---------------------------------------------
                // RIGHT CHILD
                // ---------------------------------------------

                if (right < heapSize) {

                        events.add(
                                        new CompareEvent(
                                                        largest,
                                                        right));

                        if (array[right] > array[largest]) {

                                largest = right;
                        }
                }

                // ---------------------------------------------
                // SWAP AND RECURSE
                // ---------------------------------------------

                if (largest != root) {

                        swap(
                                        array,
                                        root,
                                        largest);

                        events.add(
                                        new SwapEvent(
                                                        root,
                                                        largest));

                        heapify(
                                        array,
                                        heapSize,
                                        largest,
                                        events);
                }
        }

        private void swap(
                        int[] array,
                        int first,
                        int second) {

                int temp = array[first];

                array[first] = array[second];

                array[second] = temp;
        }
}