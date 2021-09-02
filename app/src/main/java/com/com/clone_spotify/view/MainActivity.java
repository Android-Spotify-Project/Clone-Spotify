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
import android.widget.Toolbar;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;
import com.com.clone_spotify.view.fragments.HomeFragment;
import com.com.clone_spotify.view.fragments.LibraryFragment;
import com.com.clone_spotify.view.fragments.SearchMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


public class MainActivity extends CustomAppBarActivity  {

    public RequestManager glide;

    private static final String TAG = "MainActivity2";
    private MainActivity mContext = MainActivity.this;

    private FragmentContainerView nav_host_fragment;
    private BottomNavigationView bnv;


    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private Fragment LibraryFragment;
    private RecyclerView reLibrary;

    //appbar
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: 온 크레이트 실행 됨");
        //bottomNavigationView.setSelectedItemId(R.id.bottom_nav);

        init();
        initLr();

        //fragment 초기화면 설정 ==> initLr 이 먼저 실행되고 또 newInstance 하기때문에 2번실행이됨. 그래서 주석처리 함.
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.fragmentContainer, HomeFragment.newInstance()).commit();
        
    }

    /*내비게이션 화면이동=========================================================================================*/

    public void init(){
        Log.d(TAG, "init: ");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        toolbar = findViewById(R.id.toolbarMain);

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
                        selectedFragment = new HomeFragment(mContext);
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
        Log.d(TAG, "replaceFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();

    }



}