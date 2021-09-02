package com.com.clone_spotify.view.auth;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.com.clone_spotify.R;
import com.com.clone_spotify.view.CustomAppBarActivity;
import com.com.clone_spotify.view.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class FirebaseUIActivity extends CustomAppBarActivity {

    private static final String TAG = "FirebaseUIActivity";

    private FirebaseUIActivity mContext = FirebaseUIActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("로그인 하기");
        ab.setDisplayHomeAsUpEnabled(true);


        login();
    }

    @Override
    protected void onAppBarSettings(boolean isBackButton, String title) {



        super.onAppBarSettings(isBackButton, title);
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
        Log.d(TAG, "signIn: 구글 로그인 화면 으로 이동");

    }


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // 로그인 성공시
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d(TAG, "onSignInResult: 로그인 완료");
            Log.d(TAG, "onSignInResult: "+user.getEmail());
            Log.d(TAG, "onSignInResult: "+user.getUid());
            Log.d(TAG, "onSignInResult: "+user.getProviderId());
            Intent intent = new Intent(mContext , MainActivity.class);
            startActivity(intent);
            Log.d(TAG, "onSignInResult: 인텐트 실행됨");
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