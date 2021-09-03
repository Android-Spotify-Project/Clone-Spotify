package com.com.clone_spotify.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.com.clone_spotify.R;
import com.com.clone_spotify.adapters.LibraryAdapter;
import com.com.clone_spotify.model.Library;
import com.com.clone_spotify.view.InitMainActivity;
import com.com.clone_spotify.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class LibraryFragment extends Fragment implements LibraryAdapter.IMediaSelector{
    
    private static final String TAG = "LibraryFragment";
    private InitMainActivity mIMainActivity;
    private RecyclerView reLibrary;
    private LibraryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MediaMetadataCompat> mMediaList = new ArrayList<>();
    private MediaMetadataCompat mSelectedMedia;



    public static LibraryFragment newInstance(){
        return new LibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 온크레이트");
        //appbar setting
//        ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
//        ab.setTitle("내 라이브러리");
//        ab.setDisplayHomeAsUpEnabled(true);
        setRetainInstance(true);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_library,container,false);
        Log.d(TAG, "onCreateView: 온크레이트 뷰");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: 온뷰 크레이트");
        reLibrary = view.findViewById(R.id.reLibrary); // 라이브러리 아이디 연결
        reLibrary.setHasFixedSize(true); //
        layoutManager = new LinearLayoutManager(getActivity());
        reLibrary.setLayoutManager(layoutManager);
        adapter = new LibraryAdapter(getActivity(), mMediaList, this);
        reLibrary.setAdapter(adapter);
        if(mMediaList.size() == 0){
            retrieveMedia();
        }

        if(savedInstanceState != null){
            adapter.setSelectedIndex(savedInstanceState.getInt("selected_index"));
        }

    }




    private void retrieveMedia(){
//        mIMainActivity.showProgressBar();

        FirebaseDatabase database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        DatabaseReference databaseReference = database.getReference("Library");
        // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: 데이터 받아옴");
                // 파이어베이스 데이터베이스의 데이터를 받아 오는 곳
                mMediaList.clear(); // 기존 배열 초기화 해주고
                // 반복문으로 데이터 list 추출함
                Log.d(TAG, "onDataChange: 클리어 완료");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // 만들어 졌던 Library 모델에 데이터를 담음
                    Library library = snapshot.getValue(Library.class);
                    // 담은 데이터들을 배열리스에 넣고 리사이클러뷰에 보낼 준비 함.
                    addToMediaList(library);
                }
                updateDataSet();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생시
                Log.e(TAG, "onCancelled: ", error.toException());
            }
        });
    }

    private void addToMediaList(Library library) {
        MediaMetadataCompat media = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, library.getMediaId())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, library.getArtistName())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, library.getSongTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, library.getSongUrl())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, library.getSongTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, library.getAlbumImg())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, library.getArtistName())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, library.getAlbumImg())
                .build();

        mMediaList.add(media);
    }

    private void updateDataSet(){
        Log.d(TAG, "updateDataSet: 업데이트 실행");
//        mIMainActivity.hideProgressBar();
        adapter.notifyDataSetChanged();
//        getSelectedMediaItem(mIMainActivity.getMyPreferenceManager().getLastPlayedMedia());
    }

    @Override
    public void onMediaSelected(int position) {
        mIMainActivity.getMyApplicationInstance().setMediaItems(mMediaList);
        mSelectedMedia = mMediaList.get(position);
        adapter.setSelectedIndex(position);
        mIMainActivity.onMediaSelected(
                mMediaList.get(position),
                position);
//        saveLastPlayedSongProperties();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mIMainActivity = (InitMainActivity) getActivity();
    }

    public void updateUI(MediaMetadataCompat mediaItem){
        adapter.setSelectedIndex(adapter.getIndexOfItem(mediaItem));
        mSelectedMedia = mediaItem;
//        saveLastPlayedSongProperties();
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_index", adapter.getSelectedIndex());
    }
}