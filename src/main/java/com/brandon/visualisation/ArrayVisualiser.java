package com.brandon.visualisation;

import com.brandon.models.ArrayModel;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.function.IntConsumer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 20;

    private final List<Rectangle> bars = new ArrayList<>();

    private final int[] originalValues;

    private final Set<Integer> sorted = new HashSet<>();

    private final Set<Integer> compared = new HashSet<>();

    private IntConsumer barClickListener;

    private Integer pivot = null;

    public ArrayVisualiser(ArrayModel model) {

        originalValues = model.getValues();

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(2);

        createBars();

        render();
    }

    private void createBars() {

        for (int value : originalValues) {

            Rectangle bar = new Rectangle();

            bar.setWidth(BAR_WIDTH);
            bar.setHeight(value);

            bars.add(bar);

            getChildren().add(bar);

            final int index = bars.size() - 1;

            bar.setOnMouseClicked(e -> {

                if (barClickListener != null) {
                    barClickListener.accept(index);
                }

            });
        }
    }

    public void setBarClickListener(
            IntConsumer listener) {

        this.barClickListener = listener;
    }

    public void reset() {

        sorted.clear();
        compared.clear();
        pivot = null;

        for (int i = 0; i < bars.size(); i++) {

            bars.get(i)
                    .setHeight(originalValues[i]);
        }

        render();
    }

    public void swapBars(int a, int b) {

        double temp = bars.get(a).getHeight();

        bars.get(a)
                .setHeight(bars.get(b).getHeight());

        bars.get(b)
                .setHeight(temp);
    }

    public void setBarHeight(int index, int value) {

        bars.get(index)
                .setHeight(value);
    }

    public void compare(int a, int b) {

        compared.clear();

        compared.add(a);
        compared.add(b);

        render();
    }

    public void searchCompare(int index) {

        reset();

        bars.get(index)
                .setFill(Color.ORANGE);
    }

    public void markFound(int index) {

        bars.get(index)
                .setFill(Color.LIMEGREEN);
    }

    public void markNotFound() {

        for (Rectangle bar : bars) {
            bar.setFill(Color.CRIMSON);
        }
    }

    public void markSorted(int index) {

        sorted.add(index);

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

            if (sorted.contains(i)) {

                bars.get(i)
                        .setFill(Color.LIMEGREEN);

            } else if (pivot != null && pivot == i) {

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