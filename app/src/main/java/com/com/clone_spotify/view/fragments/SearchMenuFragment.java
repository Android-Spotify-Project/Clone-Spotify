package com.com.clone_spotify.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.clone_spotify.R;
import com.com.clone_spotify.view.MainActivity;
import com.google.android.material.card.MaterialCardView;

public class SearchMenuFragment extends Fragment {

    private static final String TAG = "SearchMenuFragment";
    //    private SearchMenuFragment mContext = SearchMenuFragment.this;
    private MainActivity mContext;
    private TextView tvSearchBar;
    private MaterialCardView card1;


    public SearchMenuFragment(MainActivity mContext) {

    }

    public SearchMenuFragment() { /*빈 생성자*/ }


    //각 fragment마다 반환해줄 메소드
    public static SearchMenuFragment newInstance(){
        return new SearchMenuFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_menu, container, false);
        tvSearchBar = view.findViewById(R.id.tvSearchBar);
        card1 = view.findViewById(R.id.card1);

        //iv1 클릭시 내 라이브러리로 이동
        card1.setOnClickListener(v -> {

            Log.d(TAG, "onCreateView: 내 라이브러리 클릭됨");
            ((MainActivity)getActivity()).replaceFragment(LibraryFragment.newInstance());

        });

        // searchbar 누르면 결과 list 뿌릴 빈화면으로 이동
        tvSearchBar.setOnClickListener(v-> {

            Log.d(TAG, "onCreateView: 검색창 클릭됨");
           ((MainActivity)getActivity()).replaceFragment(SearchFragment.newInstance());

        });

        return view;


    }

}