package com.brandon.visualisation;

import com.brandon.models.ArrayModel;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ArrayVisualiser extends HBox {

    private static final double BAR_WIDTH = 12;

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

    public void highlightBars(int firstIndex, int secondIndex) {

        resetColors();

        bars.get(firstIndex).setFill(Color.RED);
        bars.get(secondIndex).setFill(Color.RED);
    }

    public void resetColors() {

        for (Rectangle bar : bars) {
            bar.setFill(Color.CORNFLOWERBLUE);
        }
    }

    public void swapBars(int firstIndex, int secondIndex) {

        Rectangle firstBar = bars.get(firstIndex);
        Rectangle secondBar = bars.get(secondIndex);

        double tempHeight = firstBar.getHeight();

        firstBar.setHeight(secondBar.getHeight());
        secondBar.setHeight(tempHeight);
    }
}
