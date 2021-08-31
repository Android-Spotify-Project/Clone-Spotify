package com.com.clone_spotify.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.com.clone_spotify.R;
import com.com.clone_spotify.model.Library;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private static final String TAG = "LibraryAdapter";
    private ArrayList<Library> arrayList;
    private Context context;



    //private LibraryAdapter mContext;

    public LibraryAdapter(ArrayList<Library> arrayList, Context Context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void includesForDownloadFiles() throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference from an HTTPS URL
        // Note that in the URL, characters are URL escaped!
        StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/com-cos-clonespotifylogin.appspot.com/o/Bruno%20Mars%2C%20Anderson%20.Paak%2C%20Silk%20Sonic%20-%20Leave%20the%20Door%20Open.png?alt=media&token=157d3b00-7420-4cfd-a455-127df3de0c58");
        Log.d(TAG, "includesForDownloadFiles: 다운로드 ");
    }


    @NonNull
    @Override
    // ViewHolder 만듬
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent,false);
        LibraryViewHolder holder = new LibraryViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: 온크레이트 뷰 홀더 만듬");

        return holder;
    }

    @Override
    // 각 아이템들에 대한 매치를 시켜줌.
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        // 에러 실행시 이미지
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        // 글라이드로 앨범 이미지 넣기
        Glide.with(holder.itemView)
                .setDefaultRequestOptions(requestOptions)
                .load(arrayList.get(position).getAlbumImg())
                .into(holder.ivAlbumImg);
        holder.tvSongTitle.setText(arrayList.get(position).getSongTitle());
        holder.tvArtistName.setText(arrayList.get(position).getArtistName());
        Log.d(TAG, "onBindViewHolder: 글라이드로 앨범 이미지 넣기");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbumImg;
        TextView tvSongTitle;
        TextView tvArtistName;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbumImg = itemView.findViewById(R.id.ivAlbumImg);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            Log.d(TAG, "LibraryViewHolder: 라이브러리 뷰 홀더");
        }
    }
}
