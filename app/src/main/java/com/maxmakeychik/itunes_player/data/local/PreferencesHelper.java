package com.maxmakeychik.itunes_player.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.maxmakeychik.itunes_player.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "itunes_player_pref_file";
    private static final String KEY_GRID_SELECTED = "grid";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public boolean gridSelected() {
        return mPref.getBoolean(KEY_GRID_SELECTED, false);
    }

    public void switchGridSelected() {
        mPref.edit().putBoolean(KEY_GRID_SELECTED, !mPref.getBoolean(KEY_GRID_SELECTED, false)).apply();
    }
}
