package com.maxmakeychik.itunes_player.ui.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.maxmakeychik.itunes_player.App;
import com.maxmakeychik.itunes_player.data.model.PlayData;
import com.maxmakeychik.itunes_player.injection.component.DaggerServiceComponent;
import com.maxmakeychik.itunes_player.injection.module.ServiceModule;
import com.maxmakeychik.itunes_player.util.MainThreadBus;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by admin on 08.08.2016.
 */
public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = "PlayerService";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "onCompleted";
    public static final String ACTION_STOP = "stop";
    public static final String ACTION_REWIND = "rewind";
    public static final String ACTION_COMPLETED = "completed";
    public static final String KEY_REWIND_TO = "rewind_to";
    public static final String KEY_URL = "url";
    public static MediaPlayer player;

    @Inject
    MainThreadBus bus;

    private Handler myHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(App.get(this).getComponent())
                .build()
                .inject(this);

        player = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }
        String action = intent.getAction();
        if (action == null) {
            return START_STICKY;
        }
        Log.d(TAG, "onStartCommand: " + action);
        switch (action) {
            case ACTION_REWIND:
                rewind(intent.getIntExtra(KEY_REWIND_TO, 0));
                break;
            case ACTION_PLAY:
                if (intent.getStringExtra(KEY_URL) != null) {
                    try {
                        play(intent.getStringExtra(KEY_URL));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.start();
                    myHandler.postDelayed(updateSongTimeRunnable, 0);
                }
                break;
            case ACTION_PAUSE:
                player.pause();
                myHandler.removeCallbacks(updateSongTimeRunnable);
                break;
            case ACTION_STOP:
                player.stop();
                player.reset();
                myHandler.removeCallbacks(updateSongTimeRunnable);
                //stopSelf();
                break;
        }
        return START_STICKY;
    }

    private void play(String url) throws IOException {
        Log.d(TAG, "play: ");
        try {
            player.setDataSource(url);
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                bus.post(new PlayData(ACTION_PLAY, player.getDuration(), player.getCurrentPosition()));
                mp.start();
                myHandler.postDelayed(updateSongTimeRunnable, 100);
            }
        });
        player.prepare();
    }

    private void rewind(int to) {
        player.seekTo(to);
    }

    private Runnable updateSongTimeRunnable = new Runnable() {
        public void run() {
            bus.post(new PlayData(ACTION_PLAY, player.getDuration(), player.getCurrentPosition()));
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(updateSongTimeRunnable);
        myHandler = null;

        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        bus.post(new PlayData(ACTION_COMPLETED));
    }
}