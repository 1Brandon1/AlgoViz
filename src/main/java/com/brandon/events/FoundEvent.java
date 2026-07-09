package com.brandon.events;

public class FoundEvent implements AnimationEvent {

    private final int index;

    public FoundEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}