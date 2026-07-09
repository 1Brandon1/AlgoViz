package com.brandon.models;

import java.util.Random;

public class ArrayModel {

    private final int[] values;

    public ArrayModel(int size) {

        values = new int[size];

        Random random = new Random();

        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(450) + 20;
        }
    }

    public int[] getValues() {
        return values;
    }
}