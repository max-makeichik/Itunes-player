package com.maxmakeychik.itunes_player.ui.player;

import com.maxmakeychik.itunes_player.data.DataManager;
import com.maxmakeychik.itunes_player.data.model.PlayData;
import com.maxmakeychik.itunes_player.ui.base.BasePresenter;
import com.maxmakeychik.itunes_player.util.MainThreadBus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class PlayerPresenter extends BasePresenter<PlayerMvpView> {

    private static final String TAG = "PlayerPresenter";
    private final DataManager dataManager;
    private final MainThreadBus bus;
    private CompositeSubscription subscription = new CompositeSubscription();

    @Inject
    public PlayerPresenter(DataManager dataManager, MainThreadBus bus) {
        this.dataManager = dataManager;
        this.bus = bus;
    }

    @Override
    public void attachView(PlayerMvpView mvpView) {
        super.attachView(mvpView);
        bus.register(this);
    }

    @Subscribe
    public void getMessage(PlayData playData){
        switch (playData.action){
            case PlayerService.ACTION_PLAY:
                getMvpView().play(playData.duration, playData.currentPosition);
                break;
            case PlayerService.ACTION_COMPLETED:
                getMvpView().onSongPlayed();
                break;
            default:
                break;
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (subscription != null) subscription.unsubscribe();
        bus.unregister(this);
    }

}