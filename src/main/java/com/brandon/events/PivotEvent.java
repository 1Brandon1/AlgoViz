package com.brandon.events;

public class PivotEvent implements AnimationEvent {

    private final int pivotIndex;

    public PivotEvent(int pivotIndex) {
        this.pivotIndex = pivotIndex;
    }

    public int getPivotIndex() {
        return pivotIndex;
    }
}