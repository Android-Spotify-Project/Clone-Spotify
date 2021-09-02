package com.com.clone_spotify.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.com.clone_spotify.R;
import com.com.clone_spotify.model.Home;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private static final String TAG = "HomeAdapter";
    private ArrayList<Home> hArrayList;
    private Context context;

    public HomeAdapter(ArrayList<Home> hArrayList, Context context) {
        this.hArrayList = hArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        HomeViewHolder hHolder = new HomeViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: 뷰 홀더 만듬");
        return hHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg1())
                .centerCrop()
                .into(holder.ivAlbum1);
        // 앨범 이미지 클릭 했을때 
        holder.ivAlbum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                //activity.getSupportFragmentManager()
            }
        });
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg2())
                .centerCrop()
                .into(holder.ivAlbum2);
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg3())
                .centerCrop()
                .into(holder.ivAlbum3);
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg4())
                .centerCrop()
                .into(holder.ivAlbum4);
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg5())
                .centerCrop()
                .into(holder.ivAlbum5);
        Glide.with(holder.itemView)
                .load(hArrayList.get(position).getAlbumImg6())
                .into(holder.ivAlbum6);
        holder.tvTitle.setText(hArrayList.get(position).getSongTitle());
        holder.tvArtist1.setText(hArrayList.get(position).getArtistName1());
        holder.tvArtist2.setText(hArrayList.get(position).getArtistName2());
        holder.tvArtist3.setText(hArrayList.get(position).getArtistName3());
        holder.tvArtist4.setText(hArrayList.get(position).getArtistName4());
        holder.tvArtist5.setText(hArrayList.get(position).getArtistName5());
        holder.tvArtist6.setText(hArrayList.get(position).getArtistName6());
        Log.d(TAG, "onBindViewHolder: 데이터 들어옴");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return (hArrayList != null ? hArrayList.size() : 0);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivAlbum1,ivAlbum2,ivAlbum3,ivAlbum4,ivAlbum5,ivAlbum6;
        TextView tvArtist1,tvArtist2,tvArtist3,tvArtist4,tvArtist5,tvArtist6;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.ivAlbum1 = itemView.findViewById(R.id.ivAlbum1);
            this.ivAlbum2 = itemView.findViewById(R.id.ivAlbum2);
            this.ivAlbum3 = itemView.findViewById(R.id.ivAlbum3);
            this.ivAlbum4 = itemView.findViewById(R.id.ivAlbum4);
            this.ivAlbum5 = itemView.findViewById(R.id.ivAlbum5);
            this.ivAlbum6 = itemView.findViewById(R.id.ivAlbum6);
            this.tvArtist1 = itemView.findViewById(R.id.tvArtist1);
            this.tvArtist2 = itemView.findViewById(R.id.tvArtist2);
            this.tvArtist3 = itemView.findViewById(R.id.tvArtist3);
            this.tvArtist4 = itemView.findViewById(R.id.tvArtist4);
            this.tvArtist5 = itemView.findViewById(R.id.tvArtist5);
            this.tvArtist6 = itemView.findViewById(R.id.tvArtist6);
            Log.d(TAG, "HomeViewHolder: view 홀더 들어옴");
        }
    }
}
