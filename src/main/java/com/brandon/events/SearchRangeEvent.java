package com.brandon.events;

public class SearchRangeEvent implements AnimationEvent {

    private final int left;
    private final int right;

    public SearchRangeEvent(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}