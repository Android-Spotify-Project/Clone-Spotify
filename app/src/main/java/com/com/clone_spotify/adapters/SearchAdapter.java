package com.com.clone_spotify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.com.clone_spotify.R;
import com.com.clone_spotify.model.Home;
import com.com.clone_spotify.model.Song;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SongViewHolder>  {

    private static final String TAG = "SearchAdapter";

    private SearchAdapter mContext = SearchAdapter.this;
//    private Context context;

    private List<Song> songs = new ArrayList<>();

    public SearchAdapter(){/*빈 생성자*/}

////    public SearchAdapter(List<Song> songs, Context context){
////        this.songs = songs;
////        this.context = context;
////    }

    @NonNull
    @NotNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //viewHolder 생성

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = inflater.inflate(R.layout.search_item, parent, false);

        return new SongViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SongViewHolder holder, int position) {
        //data binding -> data 연결

        Song song = songs.get(position);
        holder.setSongItem(song);
    }

    @Override
    public int getItemCount() {
        //recyclerView의 총 개수 반환
        return songs.size();
    }

    //1. viewHolder
    public static class SongViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAlbum0;
        private TextView tvTitle, tvSubTitle;

        public SongViewHolder(@NonNull @NotNull View songItemView) {
            super(songItemView);

            ivAlbum0 = songItemView.findViewById(R.id.ivAlbum0);
            tvTitle = songItemView.findViewById(R.id.tvTitle);
            tvSubTitle = songItemView.findViewById(R.id.tvSubTitle);

        }

        public void setSongItem(Song song){
            tvTitle.setText(song.getTitle());
            tvSubTitle.setText(song.getSubTitle());
            Glide
                    .with(ivAlbum0.getContext())
                    .load(song.getImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_test)
                    .into(ivAlbum0);


        }
    }

    public void addItem(Song song){
        songs.add(song);
    }

    public void addItems(List<Song> songs){
        this.songs = songs;
        notifyDataSetChanged();
    }





}
