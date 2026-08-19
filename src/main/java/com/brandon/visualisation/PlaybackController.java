package com.brandon.visualisation;

public class PlaybackController {

    private int currentIndex = 0;
    private boolean playing = false;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void next() {
        currentIndex++;
    }

    public void previous() {

        if (currentIndex > 0) {
            currentIndex--;
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play() {
        playing = true;
    }

    public void pause() {
        playing = false;
    }

    public void reset() {

        currentIndex = 0;
        playing = false;
    }
}