package com.brandon.events;

public class OverwriteEvent implements AnimationEvent {

    private final int index;
    private final int newValue;

    public OverwriteEvent(int index, int newValue) {
        this.index = index;
        this.newValue = newValue;
    }

    public int getIndex() {
        return index;
    }

    public int getNewValue() {
        return newValue;
    }
}