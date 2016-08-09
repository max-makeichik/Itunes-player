package com.maxmakeychik.itunes_player.data.model.songs;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by admin on 18.05.2016.
 */
@Parcel
public class Song {

    public Song(){}

    public Song(String url) {
    }

    public String kind;
    public String artistName;
    public String trackName;
    @SerializedName("artworkUrl100")
    public String imageUrl;
    public String previewUrl;

    @Override
    public String toString() {
        return "Song{" +
                "kind='" + kind + '\'' +
                ", artistName='" + artistName + '\'' +
                ", trackName='" + trackName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public boolean hasNeededKind() {
        return "song".equals(kind);
    }
}
