package com.com.clone_spotify.view.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.com.clone_spotify.R;
import com.com.clone_spotify.adapters.LibraryAdapter;
import com.com.clone_spotify.model.Library;
import com.com.clone_spotify.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class LibraryFragment extends Fragment {
    
    private static final String TAG = "LibraryFragment";

    private RecyclerView reLibrary;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Library> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private ImageView ivAlbumImg;

    private MainActivity mContext;


//    private LibraryFragment mContext = LibraryFragment.this;

    public LibraryFragment() {

    }

    public static LibraryFragment newInstance(){
        return new LibraryFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 온크레이트");
    }

    public LibraryFragment(MainActivity mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_library,container,false);
        Log.d(TAG, "onCreateView: 온크레이트 뷰");
        reLibrary = rootView.findViewById(R.id.reLibrary); // 라이브러리 아이디 연결
        reLibrary.setHasFixedSize(true); //
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        reLibrary.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // 라이브러리 객체를 담은 어레이 리스트 (어댑터 쪽으로)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

        databaseReference = database.getReference("Library"); // DB 테이블 연결

        firebaseStorage = FirebaseStorage.getInstance();
        // 저장소를 참조하는 파일명으로 지정
        storageReference = firebaseStorage.getReference().child("AlbumImg/ex01.png");
        //StorageReference 에서 파일 다운로드 URL 가져옴
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Log.d(TAG, "onComplete: getDownloadUrl");
                if (task.isSuccessful()){
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(mContext)
                            .load(task.getResult())
                            .override(80,80)
                            .into(ivAlbumImg);
                } else {
                    // URL 을 가져오지 못하면 토스트 메세지
                    Toast.makeText(mContext, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: 데이터 받아옴");
                // 파이어베이스 데이터베이스의 데이터를 받아 오는 곳
                arrayList.clear(); // 기존 배열 초기화 해주고
                // 반복문으로 데이터 list 추출함됨
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // 만들어 졌던 Library 모델에 데이터를 담음
                    Library library = snapshot.getValue(Library.class);
                    // 담은 데이터들을 배열리스에 넣고 리사이클러뷰에 보낼 준비 함.
                    arrayList.add(library);
                }
                // 리스트 저장 및 새로 고침
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생시
                Log.e(TAG, "onCancelled: ", error.toException());
            }

        });
        adapter = new LibraryAdapter(arrayList, getContext());
        // 리사이클러뷰에 어댑터 연결
        reLibrary.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }


}