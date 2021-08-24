package com.com.clone_spotify.view.auth;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.com.clone_spotify.R;
import com.com.clone_spotify.view.InitActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class StartActivity extends AppCompatActivity implements InitActivity {

    private static final String TAG = "StartActivity";

    private Button btnJoin,btnTextLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG, "onCreate: 앱 시작됨");

        signin();
        init();
        initLr();
        initSetting();
    }



    @Override
    public void init() {
//        btnJoin.findViewById(R.id.btnJoin);
//        btnTextLinkLogin.findViewById(R.id.btnTextLinkLogin);
    }

    @Override
    public void initLr() {
//        btnJoin.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
//            startActivity(intent);
//        });
    }

    @Override
    public void initSetting() {

    }

    private void signin(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_icon) // Set logo drawable
                .setTheme(R.style.Theme_CloneSpotify)
                //.setTheme(R.style.)      // Set theme
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
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d(TAG, "onSignInResult: 로그인 완료");
            Log.d(TAG, "onSignInResult: "+user.getEmail());
            Log.d(TAG, "onSignInResult: "+user.getUid());
            Log.d(TAG, "onSignInResult: "+user.getProviderId());
            // ...
        } else {
            // getIdpResponse() 에 로그인 정보가 들어 있음
            Log.d(TAG, "onSignInResult: 로그인 실패"+result.getIdpResponse().getError());
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }




}