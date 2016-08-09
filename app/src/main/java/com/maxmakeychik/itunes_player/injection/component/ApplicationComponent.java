package com.maxmakeychik.itunes_player.injection.component;

import android.app.Application;
import android.content.Context;

import com.maxmakeychik.itunes_player.data.DataManager;
import com.maxmakeychik.itunes_player.data.local.PreferencesHelper;
import com.maxmakeychik.itunes_player.injection.ApplicationContext;
import com.maxmakeychik.itunes_player.injection.module.ApplicationModule;
import com.maxmakeychik.itunes_player.util.MainThreadBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    MainThreadBus eventBus();
}