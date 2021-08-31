package com.com.clone_spotify.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;
import com.com.clone_spotify.view.fragments.HomeFragment;
import com.com.clone_spotify.view.fragments.LibraryFragment;
import com.com.clone_spotify.view.fragments.SearchMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint // 안드로이드 컴포넌트(서비스 프레그먼트 포함)에 inject 하려면 필요한 어노테이션
public class MainActivity extends AppCompatActivity  {

    // 인젝션 확인코드 입니다.
    @Inject
    public RequestManager glide;

    private static final String TAG = "MainActivity2";
    private MainActivity mContext = MainActivity.this;

    private FragmentContainerView nav_host_fragment;
    private BottomNavigationView bnv;


    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private Fragment LibraryFragment;
    private RecyclerView reLibrary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bottomNavigationView.setSelectedItemId(R.id.bottom_nav);

        init();
        initLr();

        //fragment 초기화면 설정
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, HomeFragment.newInstance()).commit();

    }

    /*내비게이션 화면이동=========================================================================================*/

    public void init(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }

    public void initLr(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new HomeFragment()).commit();

        //네비게이션 누르면 각각 화면 뿌리기
        bottomNavigationView.setOnItemSelectedListener(item ->  {

            Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.library:
                        Log.d(TAG, "initLr: 라이브러리 클릭");
                        selectedFragment = new LibraryFragment(mContext);
                        break;

                    case R.id.searchFragment:
                        Log.d(TAG, "initLr: 서치 프레그먼트 클릭 ");
                        selectedFragment = new SearchMenuFragment(mContext);

                        break;

                    case R.id.homeFragment:
                        Log.d(TAG, "initLr: 홈프레그먼트 클릭");
                        selectedFragment = new HomeFragment();
                        break;

                }
            //뿌려질 화면 위치
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit();


             return true;

        });
    }

    public void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();

    }



}