package com.maxmakeychik.itunes_player.ui.songs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxmakeychik.itunes_player.R;
import com.maxmakeychik.itunes_player.data.model.songs.Song;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Song> songs;
    private Context context;

    private static final String TAG = "SongsAdapter";
    private boolean grid;

    public SongsAdapter(boolean grid, List<Song> songs, Context context) {
        Log.d(TAG, "SongsAdapter: " + grid);
        this.songs = songs;
        this.context = context;
        this.grid = grid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(grid ? R.layout.item_song_grid : R.layout.item_song_list, parent, false));
    }

    private Song getItem(int position){
        return songs.get(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolder holderItem = (ViewHolder) holder;
        holderItem.artist.setText(getItem(position).artistName);
        holderItem.name.setText(getItem(position).trackName);

        Picasso.with(context)
                .load(getItem(position).imageUrl)
                .fit()
                .into(holderItem.image);
    }

    public void notifyRemoveEach() {
        for (int i = 0; i < songs.size(); i++) {
            notifyItemRemoved(i);
        }
    }

    public void notifyAddEach() {
        for (int i = 0; i < songs.size(); i++) {
            notifyItemInserted(i);
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void clear() {
        this.songs = Collections.emptyList();
        notifyDataSetChanged();
    }

    public void notifyChanges(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.artist)
        TextView artist;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}