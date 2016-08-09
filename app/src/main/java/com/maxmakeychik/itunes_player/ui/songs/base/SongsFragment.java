package com.maxmakeychik.itunes_player.ui.songs.base;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.ui.songs.SongsAdapter;
import com.maxmakeychik.itunes_player.util.view.RecyclerViewEmptySupport;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 09.08.2016.
 */
public class SongsFragment extends Fragment {

    public static final String KEY_SONGS = "songs";
    private static final String TAG = "SongsFragment";

    @BindView(R.id.songs_list)
    public RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.songs_empty)
    public View empty;

    public List<Song> songs = new ArrayList<>();
    public SongsAdapter songsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setRetainInstance(true);
        if(getArguments() != null){
            songs = Parcels.unwrap(getArguments().getParcelable(KEY_SONGS));
        }
        if(savedInstanceState != null){
            songs = Parcels.unwrap(savedInstanceState.getParcelable(KEY_SONGS));
        }
    }

    public void showSongs(List<Song> songs){
        Log.d(TAG, "showSongs: ");
        this.songs = songs;
        if(songs.size() == 0){
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.GONE);
        }
        recyclerView.scrollToPosition(0);
        songsAdapter.notifyChanges(songs);
    }

    public void clear() {
        songs.clear();
        songsAdapter.clear();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SONGS, Parcels.wrap(songs));
    }

    public List<Song> getSongs() {
        return songs;
    }


}
