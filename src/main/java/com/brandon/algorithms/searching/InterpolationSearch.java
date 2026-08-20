package com.brandon.algorithms.searching;

import com.brandon.algorithms.SearchingAlgorithm;
import com.brandon.events.AnimationEvent;
import com.brandon.events.FoundEvent;
import com.brandon.events.NotFoundEvent;
import com.brandon.events.SearchCompareEvent;
import com.brandon.events.SearchRangeEvent;
import com.brandon.events.SortArrayEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterpolationSearch implements SearchingAlgorithm {

        @Override
        public List<AnimationEvent> generateEvents(int[] array, int target) {
                List<AnimationEvent> events = new ArrayList<>();

                // Sort array
                int[] sorted = Arrays.copyOf(array, array.length);
                Arrays.sort(sorted);

                events.add(new SortArrayEvent());

                // Empty array
                if (sorted.length == 0) {
                        events.add(new NotFoundEvent());
                        return events;
                }

                int low = 0;
                int high = sorted.length - 1;

                while (low <= high &&
                                target >= sorted[low] &&
                                target <= sorted[high]) {

                        // Target found at boundary
                        if (sorted[low] == sorted[high]) {

                                events.add(new SearchRangeEvent(low, high));
                                events.add(new SearchCompareEvent(low));

                                if (sorted[low] == target) {

                                        events.add(new FoundEvent(low));
                                } else {

                                        events.add(new NotFoundEvent());
                                }

                                return events;
                        }

                        int position = low
                                        + ((target - sorted[low])
                                                        * (high - low))
                                                        / (sorted[high] - sorted[low]);

                        events.add(new SearchRangeEvent(low, high));
                        events.add(new SearchCompareEvent(position));

                        if (sorted[position] == target) {

                                events.add(new FoundEvent(position));
                                return events;
                        }

                        if (sorted[position] < target) {
                                low = position + 1;
                        } else {
                                high = position - 1;
                        }
                }

                // Not found
                events.add(new NotFoundEvent());
                return events;
        }
}