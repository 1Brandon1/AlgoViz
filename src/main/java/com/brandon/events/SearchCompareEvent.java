package com.brandon.events;

public class SearchCompareEvent implements AnimationEvent {

    private final int index;

    public SearchCompareEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}