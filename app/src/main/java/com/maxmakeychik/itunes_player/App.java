package com.maxmakeychik.itunes_player;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.maxmakeychik.itunes_player.injection.component.ApplicationComponent;
import com.maxmakeychik.itunes_player.injection.component.DaggerApplicationComponent;
import com.maxmakeychik.itunes_player.injection.module.ApplicationModule;
import io.fabric.sdk.android.Fabric;

/**
 * Created by admin on 01.05.2016.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;
    private static Context context;

    public static Context getAppContext() {
        return App.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        App.context = getApplicationContext();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}