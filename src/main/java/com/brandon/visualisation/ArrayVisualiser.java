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

    private static final double BAR_WIDTH = 20;

    private final List<Rectangle> bars = new ArrayList<>();

    /*
     * The values currently represented by the bars.
     *
     * This is important because the array can change
     * during sorting or Binary Search preparation.
     */
    private final int[] originalValues;
    private final int[] currentValues;

    private final Set<Integer> sorted = new HashSet<>();
    private final Set<Integer> compared = new HashSet<>();

    private IntConsumer barClickListener;

    private Integer pivot = null;

    public ArrayVisualiser(ArrayModel model) {

        originalValues = model.getValues().clone();
        currentValues = originalValues.clone();

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(2);

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

    public void searchCompare(
            int index) {

        /*
         * Do NOT call reset() here.
         *
         * Binary Search may already have sorted the
         * displayed array using OverwriteEvents.
         */
        compared.clear();

        compared.add(index);

        render();
    }

    // -------------------------------------------------
    // FOUND
    // -------------------------------------------------

    public void markFound(
            int index) {

        compared.clear();

        bars.get(index)
                .setFill(Color.LIMEGREEN);
    }

    // -------------------------------------------------
    // NOT FOUND
    // -------------------------------------------------

    public void markNotFound() {

        compared.clear();

        for (Rectangle bar : bars) {

            bar.setFill(Color.CRIMSON);
        }
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

            if (sorted.contains(i)) {

                bars.get(i)
                        .setFill(Color.LIMEGREEN);

            } else if (pivot != null &&
                    pivot == i) {

                bars.get(i)
                        .setFill(Color.MEDIUMPURPLE);

            } else if (compared.contains(i)) {

                bars.get(i)
                        .setFill(Color.ORANGE);

            } else {

                bars.get(i)
                        .setFill(Color.CORNFLOWERBLUE);
            }
        }
    }
}