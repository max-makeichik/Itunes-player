package com.maxmakeychik.itunes_player.injection.component;

import com.maxmakeychik.itunes_player.injection.PerActivity;
import com.maxmakeychik.itunes_player.injection.module.ActivityModule;
import com.maxmakeychik.itunes_player.ui.player.PlayerActivity;
import com.maxmakeychik.itunes_player.ui.songs.SongsActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SongsActivity songsActivity);

    void inject(PlayerActivity playerActivity);
}