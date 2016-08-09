package com.maxmakeychik.itunes_player.data.remote;

import com.maxmakeychik.itunes_player.data.model.songs.SongsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("/search")
    Observable<SongsResponse> getSongs(@Query("term") String term);
}
