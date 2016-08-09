package com.maxmakeychik.itunes_player.ui.songs;

import com.maxmakeychik.itunes_player.ui.base.MvpView;

public interface SongsMvpView extends MvpView {

    void showError();

    void setFabIcon(int i);
}
