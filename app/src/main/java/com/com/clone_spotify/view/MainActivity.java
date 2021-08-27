package com.com.clone_spotify.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login();
    }

    public void login(){
        // 로그인 인증 종류 빌드
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // 로그인 화면 만듬
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_icon) // Set logo drawable
                .setTheme(R.style.Theme_CloneSpotify)
                .build();
        signInLauncher.launch(signInIntent);

        Log.d(TAG, "signin: 구글 로그인 화면 으로 이동");

    }


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                    Log.d(TAG, "onActivityResult: 로그인 완료된 후 콜백 ");
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // 로그인 성공시
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d(TAG, "onSignInResult: 로그인 완료");
            Log.d(TAG, "onSignInResult: "+user.getEmail());
            Log.d(TAG, "onSignInResult: "+user.getUid());
            Log.d(TAG, "onSignInResult: "+user.getProviderId());
            // ...
        } else {
            // getIdpResponse() 에 로그인 정보가 들어 있음
            Log.d(TAG, "onSignInResult: 로그인 실패"+result.getIdpResponse().getError());
            // 로그인 실패시. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}