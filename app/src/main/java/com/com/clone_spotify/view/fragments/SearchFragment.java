package com.com.clone_spotify.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.com.clone_spotify.R;
import com.com.clone_spotify.adapters.SearchAdapter;
import com.com.clone_spotify.model.Song;
import com.com.clone_spotify.view.InitActivity;
import com.com.clone_spotify.view.MainActivity;
import com.com.clone_spotify.view.viewmodel.SearchViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private Context mContext;

    private RecyclerView rvSearch;
    private RecyclerView.LayoutManager layoutManager;
    private SearchAdapter searchAdapter;
    private MaterialSearchBar searchBar;

    private SearchViewModel searchViewModel;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("songs");
    private List<Song> songs = new ArrayList<>();


    //각각 서로 부를 fragment
    //fragment 간 화면이동
    public static SearchFragment newInstance(){
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        rvSearch = v.findViewById(R.id.rv1);
        rvSearch.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(mContext, 2);
        rvSearch.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter();
        searchBar = v.findViewById(R.id.searchBar);

        mSearchSetting();

        // true 로 설정해 줄 경우 ViewGroup의 자식 View로 자동으로 추가됩니다. 이때 root는 null 일 수 없습니다.
        // Inflate the layout for this fragment

        ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
        ab.setTitle("검색하기");
        ab.setIcon(R.drawable.ic_nav_search);
        ab.setDisplayHomeAsUpEnabled(true);



        return v;
    }

    public void mSearchSetting(){
        //검색창 설정
        Log.d(TAG, "mSearchSetting: 검색하기 설정 시작됨");

        searchBar.setHint("검색하기");
        searchBar.setText("");

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력 전처리

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //입력 동시처리
                String searchResult = searchBar.getText().trim();
                Log.d(TAG, "onTextChanged: @@여기@@ : "+ searchBar.getText()); //그대로 나옴

                searchAction(searchResult);

            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력 후처리


            }
        });

    }

    public void searchAction(String searchResult){
        Log.d(TAG, "searchAction: search 동작 시작됨");

        //아 mvvm 해야하는데... 일단 나오는지 테스트
        colRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        songs.clear();//검색전 songs List 초기화

                        for (QueryDocumentSnapshot document : task.getResult()){

                            Log.d(TAG, "onComplete: "+document.getData()); //모든 데이터

                            if (document.getData().containsValue(searchResult)){
                                //여기 실행이 안되네...? -> 자꾸 꺼짐
                                Log.d(TAG, "onComplete: "+searchResult);
                                Log.d(TAG, "onComplete: 1 "+document.getData());

                                //problems 1. 검색 찾을때 대소문자 구분이 안됨

                                //그 데이터를 각자 담아서 adapter 에 뿌리기
                                String mediaId = document.getData().get("mediaId").toString();
                                String title = document.getData().get("title").toString();
                                String subTitle = document.getData().get("subTitle").toString();
                                String songUrl = document.getData().get("songUrl").toString();
                                String imageUrl = document.getData().get("imageUrl").toString();
                                String userId = document.getData().get("userId").toString();

                                Song song = new Song(mediaId, title, subTitle, songUrl, imageUrl, userId);
                                songs.add(song);

                                searchAdapter.addItems(songs);
                                rvSearch.setAdapter(searchAdapter);
                            }
                        }
                    }
                });


    }

}