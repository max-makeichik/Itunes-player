package com.maxmakeychik.itunes_player.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.maxmakeychik.itunes_player.data.model.songs.Song;

public class Db {

    public Db() { }

    public abstract static class SongTable {
        public static final String TABLE_NAME = "subscription";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URL = "url";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_URL + " TEXT NOT NULL" +
                        " );";

        public static final String INSERT_1 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('sample1', 'http://www.feedforall.com/sample.xml'" +
                ");";
        public static final String INSERT_2 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('sample-feed', 'http://www.feedforall.com/sample-feed.xml'" +
                ");";
        public static final String INSERT_3 = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_TITLE + "," + COLUMN_URL + ")" +
                " VALUES ('Netflix Top 100', 'http://dvd.netflix.com/Top100RSS'" +
                ");";
        public static final String INSERTS = INSERT_1 + INSERT_2 + INSERT_3;
        private static final String TAG = "Db";

        public static Song parseCursor(Cursor cursor) {
            Song song = new Song();
            return song;
        }

        public static ContentValues toContentValues(Song song) {
            ContentValues values = new ContentValues();
            Log.d(TAG, "toContentValues: " + song);
            return values;
        }
    }

}
