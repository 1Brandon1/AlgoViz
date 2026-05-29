package com.brandon.visualization;

import com.brandon.models.ArrayModel;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ArrayVisualizer extends HBox {

    private static final double BAR_WIDTH = 12;

    public ArrayVisualizer(ArrayModel model) {

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

            getChildren().add(bar);
        }
    }
}