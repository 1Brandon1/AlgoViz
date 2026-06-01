package com.brandon.events;

public class SwapEvent implements AnimationEvent {

    private final int firstIndex;
    private final int secondIndex;

    public SwapEvent(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getSecondIndex() {
        return secondIndex;
    }
}