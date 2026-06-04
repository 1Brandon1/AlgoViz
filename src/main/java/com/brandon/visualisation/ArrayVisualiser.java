package com.brandon.visualisation;

import com.brandon.models.ArrayModel;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 20;

    private final List<Rectangle> bars = new ArrayList<>();

    private final Set<Integer> sortedIndices = new HashSet<>();
    private Integer pivotIndex = null;

    public ArrayVisualiser(ArrayModel model) {

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(2);

        drawArray(model);
        render();
    }

    // -------------------------
    // INITIAL RENDER
    // -------------------------

    private void drawArray(ArrayModel model) {

        int[] values = model.getValues();

        for (int value : values) {

            Rectangle bar = new Rectangle();

            bar.setWidth(BAR_WIDTH);
            bar.setHeight(value);
            bar.setFill(Color.CORNFLOWERBLUE);

            bars.add(bar);
            getChildren().add(bar);
        }
    }

    // -------------------------
    // CENTRAL RENDER FUNCTION
    // -------------------------

    private void render() {

        for (int i = 0; i < bars.size(); i++) {

            Rectangle bar = bars.get(i);

            if (sortedIndices.contains(i)) {
                bar.setFill(Color.LIMEGREEN);
                continue;
            }

            if (pivotIndex != null && i == pivotIndex) {
                bar.setFill(Color.MEDIUMPURPLE);
                continue;
            }

            bar.setFill(Color.CORNFLOWERBLUE);
        }
    }

    // -------------------------
    // VISUAL ACTIONS
    // -------------------------

    public void highlightBars(int firstIndex, int secondIndex, Runnable onFinished) {

        render();

        bars.get(firstIndex).setFill(Color.ORANGE);
        bars.get(secondIndex).setFill(Color.ORANGE);

        if (pivotIndex != null) {
            bars.get(pivotIndex).setFill(Color.MEDIUMPURPLE);
        }

        if (onFinished != null) {
            onFinished.run();
        }
    }

    public void swapBars(int firstIndex, int secondIndex, Runnable onFinished) {

        Rectangle firstBar = bars.get(firstIndex);
        Rectangle secondBar = bars.get(secondIndex);

        double firstHeight = firstBar.getHeight();
        double secondHeight = secondBar.getHeight();

        Timeline animation = new Timeline(
                new KeyFrame(
                        Duration.millis(100),
                        new KeyValue(firstBar.heightProperty(), secondHeight),
                        new KeyValue(secondBar.heightProperty(), firstHeight)
                )
        );

        animation.setOnFinished(e -> {
            render();
            if (onFinished != null) onFinished.run();
        });

        animation.play();
    }

    public void setBarHeight(int index, int value, Runnable onFinished) {

        Rectangle bar = bars.get(index);

        Timeline animation = new Timeline(
                new KeyFrame(
                        Duration.millis(100),
                        new KeyValue(bar.heightProperty(), value)
                )
        );

        animation.setOnFinished(e -> {
            render();
            if (onFinished != null) onFinished.run();
        });

        animation.play();
    }

    public void highlightPivot(int index, Runnable onFinished) {

        pivotIndex = index;
        render();

        if (onFinished != null) {
            onFinished.run();
        }
    }

    public void markSorted(int index, Runnable onFinished) {

        sortedIndices.add(index);
        render();

        if (onFinished != null) {
            onFinished.run();
        }
    }
}