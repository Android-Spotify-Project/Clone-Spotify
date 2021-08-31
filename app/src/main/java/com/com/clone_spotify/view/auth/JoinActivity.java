package com.com.clone_spotify.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.com.clone_spotify.R;
import com.com.clone_spotify.model.User;
import com.com.clone_spotify.view.InitActivity;
import com.com.clone_spotify.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class JoinActivity extends AppCompatActivity implements InitActivity {

    private static final String TAG = "JoinActivity";

    // 파이어베이스 인증 처리
    private FirebaseAuth mAuth;
    // 실시간 데이터 베이스
    private DatabaseReference mRef;

    private JoinActivity mContext = JoinActivity.this;
    private TextInputEditText tiEmail,tiPassword;
    private Chip chipJoin;
    private Button btnTextLinkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Log.d(TAG, "onCreate: 회원가입 화면 시작됨");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("CloneSpotify");

        init();
        initLr();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "onStart: onStart 실행됨");
        if(currentUser != null){
            reload();
        }
    }

    @Override
    public void initLr() {
        chipJoin.setOnClickListener(view -> {
            // 회원 가입 처리 시작
            String strEmail = tiEmail.getText().toString();
            String strPassword = tiPassword.getText().toString();
            // 파이어베이스 Auth 진행
            mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // 회원가입 성공시 , update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:성공");
                                FirebaseUser user = mAuth.getCurrentUser();

                                updateUI(user);
                            } else {
                                // 회원가입 실패시 , display a message to the user.
                                Log.w(TAG, "createUserWithEmail:실패", task.getException());
                                Toast.makeText(JoinActivity.this, "Authentication 실패.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

//    private void createAccount() {
//
//    }
//
//    private void signIn() {
//        String email = ((EditText)findViewById(R.id.email)).getText().toString();
//        String password = ((EditText)findViewById(R.id.password)).getText().toString();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(JoinActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }

    private void sendEmailVerification() {
        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }


    @Override
    public void init() {
        chipJoin = findViewById(R.id.chipJoin);
        btnTextLinkLogin = findViewById(R.id.btnTextLinkLogin);
        tiEmail = findViewById(R.id.tiEmail);
        tiPassword = findViewById(R.id.tiPassword);
    }

    @Override
    public void initSetting() {

    }
}