package com.brandon.visualisation;

import com.brandon.models.ArrayModel;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntConsumer;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 22;

    private static final Color BAR_DEFAULT = Color.web("#64748B");
    private static final Color BAR_COMPARE = Color.web("#F59E0B");
    private static final Color BAR_PIVOT = Color.web("#8B5CF6");
    private static final Color BAR_SORTED = Color.web("#10B981");
    private static final Color BAR_FOUND = Color.web("#22C55E");
    private static final Color BAR_NOT_FOUND = Color.web("#EF4444");
    private static final Color BAR_OUTSIDE = Color.web("#334155");

    // Data
    private final List<Rectangle> bars = new ArrayList<>();

    private final int[] originalValues;
    private final int[] currentValues;

    // States
    private final Set<Integer> sorted = new HashSet<>();
    private final Set<Integer> compared = new HashSet<>();

    private IntConsumer barClickListener;
    private Integer pivot = null;
    private Integer searchLeft = null;
    private Integer searchRight = null;

    // =================================================
    // CONSTRUCTOR
    // =================================================
    public ArrayVisualiser(ArrayModel model) {

        originalValues = model.getValues().clone();
        currentValues = originalValues.clone();

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(4);

        createBars();
        render();
    }

    private void createBars() {

        for (int i = 0; i < currentValues.length; i++) {

            Rectangle bar = new Rectangle();
            bar.setWidth(BAR_WIDTH);
            bar.setHeight(currentValues[i]);

            // Rounded corners
            bar.setArcWidth(6);
            bar.setArcHeight(6);

            bars.add(bar);
            getChildren().add(bar);

            final int index = i;

            // Click
            bar.setOnMouseClicked(e -> {

                if (barClickListener != null) {
                    barClickListener.accept(index);
                }
            });

            // Hover
            ScaleTransition scaleUp = new ScaleTransition(
                    Duration.millis(120),
                    bar);

            scaleUp.setToX(1.04);
            scaleUp.setToY(1.04);

            ScaleTransition scaleDown = new ScaleTransition(
                    Duration.millis(120),
                    bar);

            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            bar.setOnMouseEntered(e -> {

                scaleDown.stop();
                scaleUp.playFromStart();
                bar.setOpacity(0.9);
            });

            bar.setOnMouseExited(e -> {

                scaleUp.stop();
                scaleDown.playFromStart();
                bar.setOpacity(1.0);
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
            bars.get(i).setHeight(currentValues[i]);
        }

        compared.clear();
        pivot = null;

        render();
    }

    public void setBarClickListener(IntConsumer listener) {
        this.barClickListener = listener;
    }

    public int getValueAt(int index) {
        return currentValues[index];
    }

    public void reset() {

        sorted.clear();
        compared.clear();

        pivot = null;
        searchLeft = null;
        searchRight = null;

        for (int i = 0; i < bars.size(); i++) {

            currentValues[i] = originalValues[i];

            bars.get(i).setHeight(originalValues[i]);
        }

        render();
    }

    public void swapBars(int a, int b) {

        int value = currentValues[a];
        currentValues[a] = currentValues[b];
        currentValues[b] = value;

        bars.get(a).setHeight(currentValues[a]);
        bars.get(b).setHeight(currentValues[b]);

        render();
    }

    public void setBarHeight(int index, int value) {

        currentValues[index] = value;
        bars.get(index).setHeight(value);

        render();
    }

    public void compare(int a, int b) {

        compared.clear();
        compared.add(a);
        compared.add(b);

        render();
    }

    public void setSearchRange(int left, int right) {

        searchLeft = left;
        searchRight = right;
        compared.clear();

        render();
    }

    public void searchCompare(int index) {

        compared.clear();
        compared.add(index);

        render();
    }

    public void markFound(int index) {

        compared.clear();

        bars.get(index).setFill(BAR_FOUND);
    }

    public void markNotFound() {

        compared.clear();

        pivot = null;
        searchLeft = null;
        searchRight = null;

        for (Rectangle bar : bars) {
            bar.setFill(BAR_NOT_FOUND);
        }
    }

    public void markSorted(int index) {

        sorted.add(index);
        render();
    }

    public void markSortComplete() {

        sorted.clear();

        for (int i = 0; i < bars.size(); i++) {
            sorted.add(i);
        }

        render();
    }

    public void pivot(int index) {

        pivot = index;
        render();
    }

    public void clearHighlights() {

        compared.clear();
        render();
    }

    private void render() {

        for (int i = 0; i < bars.size(); i++) {
            Rectangle bar = bars.get(i);

            // Outside search range
            if (searchLeft != null &&
                    searchRight != null &&
                    (i < searchLeft || i > searchRight)) {

                bar.setFill(BAR_OUTSIDE);
                continue;
            }

            if (sorted.contains(i)) {
                bar.setFill(BAR_SORTED);

            } else if (pivot != null && pivot == i) {
                bar.setFill(BAR_PIVOT);

            } else if (compared.contains(i)) {
                bar.setFill(BAR_COMPARE);

            } else {
                bar.setFill(BAR_DEFAULT);
            }
        }
    }
}