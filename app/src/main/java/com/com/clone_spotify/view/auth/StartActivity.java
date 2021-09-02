package com.com.clone_spotify.view.auth;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import com.com.clone_spotify.R;
import com.com.clone_spotify.view.InitActivity;


public class StartActivity extends AppCompatActivity implements InitActivity {

    private static final String TAG = "StartActivity";
    private StartActivity mContext = StartActivity.this;

    private Button btnJoin,btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG, "onCreate: 앱 시작됨");

        //액션바 없애기
        ActionBar ab = getSupportActionBar();
        ab.hide();

        init();
        initLr();
        initSetting();
    }


    @Override
    public void init() {
        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);
        Log.d(TAG, "init: 메모리");
    }

    @Override
    public void initLr() {
        btnJoin.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,JoinActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, FirebaseUIActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void initSetting() {

    }


}