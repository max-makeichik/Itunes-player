package com.maxmakeychik.itunes_player.injection.module;

import android.app.Service;

import com.maxmakeychik.itunes_player.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @ApplicationContext
    Service provideService() {
        return mService;
    }
}

