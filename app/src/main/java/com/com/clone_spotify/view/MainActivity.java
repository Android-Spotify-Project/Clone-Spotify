package com.com.clone_spotify.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint // 안드로이드 컴포넌트(서비스 프레그먼트 포함)에 inject 하려면 필요한 어노테이션
public class MainActivity extends AppCompatActivity {
    // 인젝션 확인코드 입니다.
    @Inject
    public RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}