package com.maxmakeychik.itunes_player.ui.songs;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.ui.songs.base.SongsFragment;
import com.maxmakeychik.itunes_player.util.ItemClickSupport;

import org.parceler.Parcels;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 15.05.2016.
 */
public class SongsListFragment extends SongsFragment {

    private static final String TAG = "SongsListFragment";

    private Unbinder unbinder;

    public static SongsListFragment newInstance(List<Song> songs) {
        SongsListFragment songsListFragment = new SongsListFragment();
        Bundle b = new Bundle();
        b.putParcelable(KEY_SONGS, Parcels.wrap(songs));
        songsListFragment.setArguments(b);
        return songsListFragment;
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_songs, container, false);
        unbinder = ButterKnife.bind(this, view);

        Log.d(TAG, "onCreateView: ");
        setList();

        return view;
    }

    private void setList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        songsAdapter = new SongsAdapter(false, songs, getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(songsAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ((SongsActivity) getActivity()).onSongClicked(songs.get(position));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}