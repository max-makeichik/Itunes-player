package com.maxmakeychik.itunes_player.data;

import com.maxmakeychik.itunes_player.data.local.DatabaseHelper;
import com.maxmakeychik.itunes_player.data.local.PreferencesHelper;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.data.remote.ApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class DataManager {

    private static final String TAG = "DataManager";
    private final ApiService apiService;
    private final DatabaseHelper databaseHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(ApiService apiService, PreferencesHelper preferencesHelper, DatabaseHelper databaseHelper) {
        this.apiService = apiService;
        this.preferencesHelper = preferencesHelper;
        this.databaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public Observable<List<Song>> getSongs(String term) {
        return apiService.getSongs(term)
                .map(songsResponse -> songsResponse.songs)
                .flatMap(Observable::from)
                .filter(Song::hasNeededKind)
                .toList();
    }

}