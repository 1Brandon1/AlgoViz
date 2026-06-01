package com.brandon.events;

public class CompareEvent implements AnimationEvent {

    private final int firstIndex;
    private final int secondIndex;

    public CompareEvent(int firstIndex, int secondIndex) {
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