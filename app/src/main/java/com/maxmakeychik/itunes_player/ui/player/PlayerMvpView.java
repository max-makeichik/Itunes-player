package com.maxmakeychik.itunes_player.ui.player;

import com.maxmakeychik.itunes_player.ui.base.MvpView;

public interface PlayerMvpView extends MvpView {

    void showError();

    void onSongPlayed();

    void play(int duration, int currentPosition);
}
