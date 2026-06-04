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
import java.util.List;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 12;

    private Integer pivotIndex = null;

    private final List<Rectangle> bars = new ArrayList<>();

    public ArrayVisualiser(ArrayModel model) {

        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(2);

        drawArray(model);
    }

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

    public void highlightBars(int firstIndex, int secondIndex, Runnable onFinished) {

        resetColors();

        bars.get(firstIndex).setFill(Color.ORANGE);
        bars.get(secondIndex).setFill(Color.ORANGE);

        if (pivotIndex != null) {
            bars.get(pivotIndex).setFill(Color.MEDIUMPURPLE);
        }

        if (onFinished != null) {
            onFinished.run();
        }
    }

    public void resetColors() {

        for (Rectangle bar : bars) {
            bar.setFill(Color.CORNFLOWERBLUE);
        }

        if (pivotIndex != null) {
            bars.get(pivotIndex).setFill(Color.MEDIUMPURPLE);
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
            if (onFinished != null) {
                onFinished.run();
            }
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
            if (onFinished != null) {
                onFinished.run();
            }
        });

        animation.play();
    }

    public void highlightPivot(int index, Runnable onFinished) {

        pivotIndex = index;

        resetColors();

        if (onFinished != null) {
            onFinished.run();
        }
    }

    public void markSorted(int index) {
        bars.get(index).setFill(Color.LIMEGREEN);
    }
}
