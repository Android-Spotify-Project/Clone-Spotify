package com.com.clone_spotify.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;
import com.com.clone_spotify.view.fragments.HomeFragment;
import com.com.clone_spotify.view.fragments.LibraryFragment;
import com.com.clone_spotify.view.viewmodel.AuthViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint // 안드로이드 컴포넌트(서비스 프레그먼트 포함)에 inject 하려면 필요한 어노테이션
public class MainActivity extends AppCompatActivity {
    // 인젝션 확인코드 입니다.
    @Inject
    public RequestManager glide;

    private static final String TAG = "MainActivity2";

    private MainActivity mContext = MainActivity.this;

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
    }


    public void init(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        fragmentContainer = findViewById(R.id.fragmentContainer);
    }

    public void initLr(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new HomeFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item ->  {

            Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.library:
                        Log.d(TAG, "initLr: 라이브러리 클릭");
                        selectedFragment = new LibraryFragment(mContext);
                        break;

                    case R.id.searchFragment:
                        Log.d(TAG, "initLr: 서치 프레그먼트 클릭 ");

                        break;

                    case R.id.homeFragment:
                        Log.d(TAG, "initLr: 홈프레그먼트 클릭");

                        break;

                }
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, selectedFragment).commit();

                return true;

        });
    }

}