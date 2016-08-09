package com.maxmakeychik.itunes_player.data.model;

/**
 * Created by admin on 08.08.2016.
 */
public class PlayData {

    public String action;
    public int duration;
    public int currentPosition;

    public PlayData(String action, int duration, int currentPosition) {
        this.action = action;
        this.duration = duration;
        this.currentPosition = currentPosition;
    }

    public PlayData(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "PlayData{" +
                "duration=" + duration +
                ", currentPosition=" + currentPosition +
                '}';
    }
}
