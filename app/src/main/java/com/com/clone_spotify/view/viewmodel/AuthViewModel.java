package com.com.clone_spotify.view.viewmodel;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.com.clone_spotify.R;
import com.com.clone_spotify.model.User;
import com.com.clone_spotify.view.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    
//    private MutableLiveData<User> user = new MutableLiveData<>();
//
//    public MutableLiveData<User> getUser(){
//        return user;
//    }


}
