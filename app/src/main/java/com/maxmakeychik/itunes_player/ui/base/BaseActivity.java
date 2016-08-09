package com.maxmakeychik.itunes_player.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.maxmakeychik.itunes_player.App;
import com.maxmakeychik.itunes_player.injection.component.ActivityComponent;
import com.maxmakeychik.itunes_player.injection.component.DaggerActivityComponent;
import com.maxmakeychik.itunes_player.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
