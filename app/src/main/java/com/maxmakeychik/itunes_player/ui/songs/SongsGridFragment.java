package com.maxmakeychik.itunes_player.ui.songs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.maxmakeychik.itunes_player.util.view.GridDividerDecoration;

import org.parceler.Parcels;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 15.05.2016.
 */
public class SongsGridFragment extends SongsFragment {

    private static final String TAG = "SongsGridFragment";
    private Unbinder unbinder;

    public static SongsGridFragment newInstance(List<Song> songs) {
        SongsGridFragment songsGridFragment = new SongsGridFragment();
        Bundle b = new Bundle();
        b.putParcelable(KEY_SONGS, Parcels.wrap(songs));
        songsGridFragment.setArguments(b);
        return songsGridFragment;
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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3, LinearLayoutManager.VERTICAL, false));

        songsAdapter = new SongsAdapter(true, songs, getActivity());
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));
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