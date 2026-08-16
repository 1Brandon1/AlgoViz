package com.brandon.visualisation;

import com.brandon.models.ArrayModel;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntConsumer;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 22;

    private final List<Rectangle> bars = new ArrayList<>();

    private final int[] originalValues;
    private final int[] currentValues;

    private final Set<Integer> sorted = new HashSet<>();
    private final Set<Integer> compared = new HashSet<>();

    private IntConsumer barClickListener;

    private Integer pivot = null;

    private Integer searchLeft = null;
    private Integer searchRight = null;

    public ArrayVisualiser(ArrayModel model) {

        originalValues = model.getValues().clone();
        currentValues = originalValues.clone();

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(4);

        createBars();

        render();
    }

    // -------------------------------------------------
    // CREATE BARS
    // -------------------------------------------------

    private void createBars() {

        for (int i = 0; i < currentValues.length; i++) {

            Rectangle bar = new Rectangle();

            bar.setWidth(BAR_WIDTH);
            bar.setHeight(currentValues[i]);

            bars.add(bar);

            getChildren().add(bar);

            final int index = i;

            bar.setOnMouseClicked(e -> {

                if (barClickListener != null) {
                    barClickListener.accept(index);
                }
            });
        }
    }

    public void sortBars() {

        List<Integer> values = new ArrayList<>();

        for (int value : currentValues) {
            values.add(value);
        }

        values.sort(Integer::compareTo);

        for (int i = 0; i < values.size(); i++) {

            currentValues[i] = values.get(i);

            bars.get(i).setHeight(
                    currentValues[i]);
        }

        compared.clear();
        pivot = null;

        render();
    }

    // -------------------------------------------------
    // BAR CLICK LISTENER
    // -------------------------------------------------

    public void setBarClickListener(
            IntConsumer listener) {

        this.barClickListener = listener;
    }

    // -------------------------------------------------
    // GET CURRENT VALUE
    // -------------------------------------------------

    public int getValueAt(int index) {

        return currentValues[index];
    }

    // -------------------------------------------------
    // RESET
    // -------------------------------------------------

    public void reset() {

        sorted.clear();
        compared.clear();

        pivot = null;

        searchLeft = null;
        searchRight = null;

        for (int i = 0; i < bars.size(); i++) {

            currentValues[i] = originalValues[i];

            bars.get(i).setHeight(
                    originalValues[i]);
        }

        render();
    }

    // -------------------------------------------------
    // SWAP
    // -------------------------------------------------

    public void swapBars(int a, int b) {

        int value = currentValues[a];

        currentValues[a] = currentValues[b];

        currentValues[b] = value;

        bars.get(a).setHeight(
                currentValues[a]);

        bars.get(b).setHeight(
                currentValues[b]);

        render();
    }

    // -------------------------------------------------
    // OVERWRITE
    // -------------------------------------------------

    public void setBarHeight(
            int index,
            int value) {

        currentValues[index] = value;

        bars.get(index)
                .setHeight(value);

        render();
    }

    // -------------------------------------------------
    // NORMAL SORT COMPARISON
    // -------------------------------------------------

    public void compare(
            int a,
            int b) {

        compared.clear();

        compared.add(a);
        compared.add(b);

        render();
    }

    // -------------------------------------------------
    // SEARCH COMPARISON
    // -------------------------------------------------

    public void setSearchRange(
            int left,
            int right) {

        searchLeft = left;
        searchRight = right;

        compared.clear();

        render();
    }

    public void searchCompare(
            int index) {

        compared.clear();

        compared.add(index);

        render();
    }

    // -------------------------------------------------
    // FOUND
    // -------------------------------------------------

    public void markFound(int index) {

        compared.clear();

        bars.get(index).setFill(Color.LIMEGREEN);
    }

    // -------------------------------------------------
    // NOT FOUND
    // -------------------------------------------------

    public void markNotFound() {

        compared.clear();
        render();
    }

    // -------------------------------------------------
    // SORTED
    // -------------------------------------------------

    public void markSorted(
            int index) {

        sorted.add(index);

        render();
    }

    // -------------------------------------------------
    // PIVOT
    // -------------------------------------------------

    public void pivot(
            int index) {

        pivot = index;

        render();
    }

    // -------------------------------------------------
    // CLEAR HIGHLIGHTS
    // -------------------------------------------------

    public void clearHighlights() {

        compared.clear();

        render();
    }

    // -------------------------------------------------
    // RENDER
    // -------------------------------------------------

    private void render() {

        for (int i = 0; i < bars.size(); i++) {

            Rectangle bar = bars.get(i);

            // Outside active Binary Search range
            if (searchLeft != null &&
                    searchRight != null &&
                    (i < searchLeft ||
                            i > searchRight)) {

                bar.setFill(Color.LIGHTGRAY);
                continue;
            }

            // Sorted
            if (sorted.contains(i)) {

                bar.setFill(Color.LIMEGREEN);

                // Pivot
            } else if (pivot != null &&
                    pivot == i) {

                bar.setFill(Color.MEDIUMPURPLE);

                // Current comparison
            } else if (compared.contains(i)) {

                bar.setFill(Color.ORANGE);

                // Normal
            } else {

                bar.setFill(Color.CORNFLOWERBLUE);
            }
        }
    }
}