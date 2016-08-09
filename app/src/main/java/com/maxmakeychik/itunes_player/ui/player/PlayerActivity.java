package com.maxmakeychik.itunes_player.ui.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.ui.base.BaseActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 10.04.2016.
 */
public class PlayerActivity extends BaseActivity implements PlayerMvpView {

    private static final String TAG = "PlayerActivity";
    private static final String TAG_PLAYER_FRAGMENT = "player_fragment";
    public static final String KEY_SONG = "song";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    PlayerPresenter postsPresenter;

    private Song song;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        setToolbar();

        if(savedInstanceState == null) {
            song = Parcels.unwrap(getIntent().getParcelableExtra(KEY_SONG));
            showPlayer(song);
        }
        else {
            song = Parcels.unwrap(savedInstanceState.getParcelable(KEY_SONG));
        }
        //Log.d(TAG, "onCreate: " + song);
        postsPresenter.attachView(this);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showPlayer(Song song){
        getFragmentManager().beginTransaction()
                .add(R.id.container, PlayerFragment.newInstance(song), TAG_PLAYER_FRAGMENT)
                .commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SONG, Parcels.wrap(song));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError() {
    }

    @Override
    public void onSongPlayed() {
        ((PlayerFragment) getFragmentManager().findFragmentByTag(TAG_PLAYER_FRAGMENT)).onCompleted();
    }

    @Override
    public void play(int duration, int currentPosition) {
        ((PlayerFragment) getFragmentManager().findFragmentByTag(TAG_PLAYER_FRAGMENT)).play(duration, currentPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        postsPresenter.detachView();
    }
}