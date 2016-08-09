package com.maxmakeychik.itunes_player.ui.player;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.maxmakeychik.itunes_player.util.view.ControlImageButton;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PlayerFragment extends Fragment {

    private static final String TAG = "PlayerFragment";
    private static final String KEY_PLAYING = "playing";

    @BindView(R.id.artist)
    TextView artist;
    @BindView(R.id.image)
    ImageView imageView;

    @BindView(R.id.pause)
    ControlImageButton pauseButton;
    @BindView(R.id.play)
    ControlImageButton playButton;

    @BindView(R.id.seekBar)
    SeekBar seekbar;
    @BindView(R.id.name)
    TextView name;

    private Unbinder unbinder;
    private Song song;

    private boolean playing = true;
    private double startTime = 0;
    private double finalTime = 0;

    public PlayerFragment() {
    }

    public static PlayerFragment newInstance(Song song) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable(PlayerActivity.KEY_SONG, Parcels.wrap(song));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            song = Parcels.unwrap(getArguments().getParcelable(PlayerActivity.KEY_SONG));
        } else if (savedInstanceState != null) {
            song = Parcels.unwrap(savedInstanceState.getParcelable(PlayerActivity.KEY_SONG));
            playing = savedInstanceState.getBoolean(KEY_PLAYING);
        }

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: send play intent");
            Intent intent = new Intent(getActivity(), PlayerService.class);
            intent.putExtra(PlayerService.KEY_URL, song.previewUrl);
            intent.setAction(PlayerService.ACTION_PLAY);
            getActivity().startService(intent);
        }
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);
        Log.d(TAG, "onCreateView: ");
        unbinder = ButterKnife.bind(this, view);

        artist.setText(song.artistName);
        name.setText(song.trackName);

        Picasso.with(getActivity())
                .load(song.imageUrl)
                .fit()
                .into(imageView);

        seekbar.setSaveEnabled(false);
        seekbar.setMax((int) finalTime);
        seekbar.setProgress((int) startTime);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PlayerService.class);
                intent.setAction(PlayerService.ACTION_REWIND);
                intent.putExtra(PlayerService.KEY_REWIND_TO, progress);
                getActivity().startService(intent);
            }
        });

        seekbar.setClickable(false);
        playButton.setEnabled(!playing);
        pauseButton.setEnabled(playing);

        return view;
    }

    @OnClick(R.id.pause)
    void onPauseClick() {
        Intent intent = new Intent(getActivity(), PlayerService.class);
        intent.setAction(PlayerService.ACTION_PAUSE);
        getActivity().startService(intent);
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
        playing = false;
    }

    @OnClick(R.id.play)
    void onPlayClick() {
        Intent intent = new Intent(getActivity(), PlayerService.class);
        intent.setAction(PlayerService.ACTION_PLAY);
        getActivity().startService(intent);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
        playing = true;
    }

    public void play(int duration, int currentPosition) {
        Log.d(TAG, "play: duration " + duration + ", currentPosition " + currentPosition);
        finalTime = duration;
        startTime = currentPosition;

        seekbar.setMax((int) finalTime);
        seekbar.setProgress((int) startTime);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    public void onCompleted() {
        playing = false;
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        seekbar = null;
        Intent intent = new Intent(getActivity(), PlayerService.class);
        intent.setAction(PlayerService.ACTION_STOP);
        getActivity().startService(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PlayerActivity.KEY_SONG, Parcels.wrap(song));
        outState.putBoolean(KEY_PLAYING, playing);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}