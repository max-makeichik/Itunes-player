package com.maxmakeychik.itunes_player.data.model.songs;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by admin on 26.07.2016.
 */

@Parcel
public class SongsResponse {

    public SongsResponse(){}

    public int resultCount;

    @SerializedName("results")
    public List<Song> songs;

    @Override
    public String toString() {
        return "SongsResponse{" +
                "songs=" + songs +
                '}';
    }
}
