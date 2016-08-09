package com.maxmakeychik.itunes_player.data.local;

import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
        //mDb.setLoggingEnabled(true);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<List<Song>> getSongs(String term) {
        return mDb.createQuery(Db.SongTable.TABLE_NAME,
                "SELECT * FROM " + Db.SongTable.TABLE_NAME)
                .mapToList(Db.SongTable::parseCursor);
    }

}