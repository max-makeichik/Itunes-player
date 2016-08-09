package com.maxmakeychik.itunes_player.ui.songs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.ui.base.BaseActivity;
import com.maxmakeychik.itunes_player.ui.player.PlayerActivity;
import com.maxmakeychik.itunes_player.util.Util;

import org.parceler.Parcels;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by admin on 10.04.2016.
 */
public class SongsActivity extends BaseActivity implements SongsMvpView {

    private static final String TAG_SONGS_LIST_FRAGMENT = "songs_list_fragment";
    private static final String TAG_SONGS_GRID_FRAGMENT = "songs_grid_fragment";
    private static final String KEY_SEARCH_QUERY = "search_query";

    @BindView(R.id.switcher)
    FloatingActionButton switcherFab;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @Inject
    SongsPresenter songsPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static String searchQuery = "";

    private static final String TAG = "SongsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        setContentView(R.layout.activity_songs);
        ButterKnife.bind(this);

        setToolbar();
        if (savedInstanceState == null) {
            showSongs();
        }
        else {
            searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY);
        }

        songsPresenter.attachView(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.title_activity_songs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_songs, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        Log.d(TAG, "onCreateOptionsMenu: " + searchQuery);
        if (searchView.getQuery().length() == 0) {
            searchView.setQuery(searchQuery, false);
            Log.d(TAG, "onCreateOptionsMenu: set searchquery" + searchQuery);
        }
        RxSearchView.queryTextChanges(searchView)
                .skip(1)
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(200, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .filter(charSequence -> {
                    if (charSequence.toString().length() == 0) {
                        return false;
                    }
                    searchQuery = charSequence.toString();
                    Log.i(TAG, "onCreateOptionsMenu: " + searchQuery);
                    final boolean empty = charSequence.length() < 5;
                    if (empty) {
                        clear();
                    }
                    return !empty;
                })
                .flatMap(charSequence -> {
                    progressBar.setVisibility(View.VISIBLE);
                    return songsPresenter.getSongs(charSequence.toString());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songs -> {
                    progressBar.setVisibility(View.GONE);
                    if (songsPresenter.isGrid()) {
                        ((SongsGridFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_GRID_FRAGMENT)).showSongs(songs);
                    } else {
                        ((SongsListFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_LIST_FRAGMENT)).showSongs(songs);
                    }
                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
                    throwable.printStackTrace();
                });

        return true;
    }

    private void clear() {
        if (songsPresenter.isGrid()) {
            ((SongsGridFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_GRID_FRAGMENT)).clear();
        } else {
            ((SongsListFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_LIST_FRAGMENT)).clear();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    public void onSongClicked(Song songId) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.KEY_SONG, Parcels.wrap(songId));
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment, String TAG) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, TAG)
                .commitAllowingStateLoss();
    }

    public void showSongs() {
        Log.d(TAG, "showSongs: ");
        replaceFragment(songsPresenter.isGrid() ? new SongsListFragment() : new SongsGridFragment(),
                songsPresenter.isGrid() ? TAG_SONGS_LIST_FRAGMENT : TAG_SONGS_GRID_FRAGMENT);

        replaceFragment(songsPresenter.isGrid() ? new SongsGridFragment() : new SongsListFragment(),
                songsPresenter.isGrid() ? TAG_SONGS_GRID_FRAGMENT : TAG_SONGS_LIST_FRAGMENT);
    }

    @Override
    public void showError() {
        Util.toastIfNoConnection(this);
    }

    @OnClick(R.id.switcher)
    void onSwitcherClicked() {
        songsPresenter.switchGrid();
        SongsGridFragment gridFragment = (SongsGridFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_GRID_FRAGMENT);
        SongsListFragment listFragment = (SongsListFragment) getFragmentManager().findFragmentByTag(TAG_SONGS_LIST_FRAGMENT);

        if (songsPresenter.isGrid()) {
            if(getFragmentManager().findFragmentByTag(TAG_SONGS_GRID_FRAGMENT) != null){
                replaceFragment(getFragmentManager().findFragmentByTag(TAG_SONGS_GRID_FRAGMENT), TAG_SONGS_GRID_FRAGMENT);
                gridFragment.showSongs(listFragment.getSongs());
            }
            else {
                replaceFragment(SongsGridFragment.newInstance(listFragment.getSongs()), TAG_SONGS_GRID_FRAGMENT);
            }
        } else {
            if(getFragmentManager().findFragmentByTag(TAG_SONGS_LIST_FRAGMENT) != null){
                replaceFragment(getFragmentManager().findFragmentByTag(TAG_SONGS_LIST_FRAGMENT), TAG_SONGS_LIST_FRAGMENT);
                listFragment.showSongs(gridFragment.getSongs());
            }
            else {
                replaceFragment(SongsListFragment.newInstance(gridFragment.getSongs()), TAG_SONGS_LIST_FRAGMENT);
            }
        }

    }

    @Override
    public void setFabIcon(int resId) {
        switcherFab.setImageDrawable(getResources().getDrawable(resId));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        songsPresenter.detachView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SEARCH_QUERY, searchQuery);
    }
}