package com.maxmakeychik.itunes_player.ui.songs;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.DataManager;
import com.maxmakeychik.itunes_player.data.local.PreferencesHelper;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SongsPresenter extends BasePresenter<SongsMvpView> {

    private static final String TAG = "SongsPresenter";
    private final DataManager dataManager;
    private final PreferencesHelper preferencesHelper;
    private CompositeSubscription subscription = new CompositeSubscription();

    @Inject
    public SongsPresenter(DataManager dataManager, PreferencesHelper preferencesHelper) {
        this.dataManager = dataManager;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void attachView(SongsMvpView mvpView) {
        super.attachView(mvpView);
        getMvpView().setFabIcon(preferencesHelper.gridSelected() ? R.drawable.list : R.drawable.grid);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
    }

    public Observable<List<Song>> getSongs(String term) {
        checkViewAttached();
        return dataManager.getSongs(term).subscribeOn(Schedulers.io());
    }

    public void switchGrid() {
        getMvpView().setFabIcon(!preferencesHelper.gridSelected() ? R.drawable.list : R.drawable.grid);
        preferencesHelper.switchGridSelected();
    }

    public boolean isGrid() {
        return preferencesHelper.gridSelected();
    }
}