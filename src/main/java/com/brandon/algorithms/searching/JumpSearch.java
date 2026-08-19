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

public class JumpSearch implements SearchingAlgorithm {

        @Override
        public List<AnimationEvent> generateEvents(int[] array, int target) {
                List<AnimationEvent> events = new ArrayList<>();

                // ---------------------------------------------
                // SORT ARRAY
                // ---------------------------------------------

                int[] sorted = Arrays.copyOf(array, array.length);
                Arrays.sort(sorted);

                events.add(new SortArrayEvent());

                // ---------------------------------------------
                // EMPTY ARRAY
                // ---------------------------------------------

                if (sorted.length == 0) {
                        events.add(new NotFoundEvent());
                        return events;
                }

                // ---------------------------------------------
                // JUMP SIZE
                // ---------------------------------------------

                int jumpSize = (int) Math.sqrt(sorted.length);
                int previous = 0;
                int current = jumpSize;

                // ---------------------------------------------
                // JUMP THROUGH ARRAY
                // ---------------------------------------------

                while (previous < sorted.length &&
                                sorted[Math.min(
                                                current,
                                                sorted.length) - 1] < target) {

                        int end = Math.min(current, sorted.length) - 1;

                        events.add(new SearchRangeEvent(previous, end));
                        events.add(new SearchCompareEvent(end));

                        previous = current;
                        current += jumpSize;

                        if (previous >= sorted.length) {
                                events.add(new NotFoundEvent());
                                return events;
                        }
                }

                // ---------------------------------------------
                // LINEAR SEARCH WITHIN BLOCK
                // ---------------------------------------------

                int end = Math.min(current, sorted.length);

                events.add(new SearchRangeEvent(previous, end - 1));

                for (int i = previous; i < end; i++) {
                        events.add(new SearchCompareEvent(i));

                        if (sorted[i] == target) {
                                events.add(new FoundEvent(i));
                                return events;
                        }

                        if (sorted[i] > target) {
                                break;
                        }
                }

                // ---------------------------------------------
                // NOT FOUND
                // ---------------------------------------------

                events.add(new NotFoundEvent());
                return events;
        }
}