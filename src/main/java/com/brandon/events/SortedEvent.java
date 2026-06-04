package com.brandon.events;

public class SortedEvent implements AnimationEvent {

    private final int index;

    public SortedEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}