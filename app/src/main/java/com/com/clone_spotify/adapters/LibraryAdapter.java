package com.com.clone_spotify.adapters;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
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
    private ArrayList<MediaMetadataCompat> mMediaList = new ArrayList<>();
    private Context mContext;
    private IMediaSelector mIMediaSelector;
    private int mSelectedIndex;

    //private LibraryAdapter mContext;

    public LibraryAdapter(Context context, ArrayList<MediaMetadataCompat> mediaList, IMediaSelector mediaSelector) {
        Log.d(TAG, "PlaylistRecyclerAdapter: called.");
        this.mMediaList = mediaList;
        this.mContext = context;
        this.mIMediaSelector = mediaSelector;
        mSelectedIndex = -1;
    }

//    public void includesForDownloadFiles() throws IOException {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//
//        // Create a reference from an HTTPS URL
//        // Note that in the URL, characters are URL escaped!
//        StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/com-cos-clonespotifylogin.appspot.com/o/Bruno%20Mars%2C%20Anderson%20.Paak%2C%20Silk%20Sonic%20-%20Leave%20the%20Door%20Open.png?alt=media&token=157d3b00-7420-4cfd-a455-127df3de0c58");
//        Log.d(TAG, "includesForDownloadFiles: 다운로드 ");
//    }


    @NonNull
    @Override
    // ViewHolder 만듬
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent,false);
        LibraryViewHolder holder = new LibraryViewHolder(view, mIMediaSelector);
        Log.d(TAG, "onCreateViewHolder: 온크레이트 뷰 홀더 만듬");

        return holder;
    }

    @Override
    // 각 아이템들에 대한 매치를 시켜줌.
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        // 에러 실행시 이미지
//        RequestOptions requestOptions = new RequestOptions()
//                .error(R.drawable.ic_launcher_background);
        // 글라이드로 앨범 이미지 넣기
        Glide.with(holder.itemView)
                //.setDefaultRequestOptions(requestOptions)
                .load(mMediaList.get(position).getDescription().getIconUri())
                .centerCrop()
                .into(holder.ivAlbumImg);
        holder.tvSongTitle.setText(mMediaList.get(position).getDescription().getTitle());
        holder.tvArtistName.setText(mMediaList.get(position).getDescription().getSubtitle());
        Log.d(TAG, "onBindViewHolder: 글라이드로 앨범 이미지 넣기");
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        // 삼항 연산자
        return (mMediaList != null ? mMediaList.size() : 0);
    }

    public void setSelectedIndex(int index){
        mSelectedIndex = index;
        notifyDataSetChanged();
    }

    public int getIndexOfItem(MediaMetadataCompat mediaItem){
        for(int i = 0; i<mMediaList.size(); i++ ){
            if(mMediaList.get(i).getDescription().getMediaId().equals(mediaItem.getDescription().getMediaId())){
                return i;
            }
        }
        return -1;
    }

    public int getSelectedIndex(){
        return mSelectedIndex;
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAlbumImg;
        private TextView tvSongTitle;
        private TextView tvArtistName;
        private IMediaSelector iMediaSelector;

        public LibraryViewHolder(@NonNull View itemView, IMediaSelector songSelector) {
            super(itemView);
            ivAlbumImg = itemView.findViewById(R.id.ivAlbumImg);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            iMediaSelector = songSelector;
            Log.d(TAG, "LibraryViewHolder: 라이브러리 뷰 홀더");
            initLr();
        }

        public void initLr(){
            itemView.setOnClickListener(view -> {
                iMediaSelector.onMediaSelected(getAdapterPosition());
            });
        }
    }

    public interface IMediaSelector{
        void onMediaSelected(int position);
    }
}
