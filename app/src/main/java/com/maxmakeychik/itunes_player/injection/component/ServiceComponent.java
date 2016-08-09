package com.maxmakeychik.itunes_player.injection.component;

import com.maxmakeychik.itunes_player.injection.PerActivity;
import com.maxmakeychik.itunes_player.injection.module.ServiceModule;
import com.maxmakeychik.itunes_player.ui.player.PlayerService;

import dagger.Component;

/**
 * Created by admin on 08.08.2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    
    void inject(PlayerService service);
    
}
