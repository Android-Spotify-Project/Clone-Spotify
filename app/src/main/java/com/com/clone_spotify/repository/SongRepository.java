package com.com.clone_spotify.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.com.clone_spotify.model.Song;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class SongRepository {
    //db에 접근하고 viewModel 에 callback
    static final String TAG = "SongRepository";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colRef =db.collection("songs");

    private MutableLiveData<List<Song>> allSongs = new MutableLiveData<>();

    public SongRepository(){
        List<Song> songs = new ArrayList<>();
        allSongs.setValue(songs);

    }

    public LiveData<List<Song>> findAllSongs(){
        //db에 접근해서 데이터 가져오기
        colRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //통신에 성공하면
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, "onComplete: "+document.getData());

                            }
                        } else{
                            Log.d(TAG, "onComplete: error"+task.getException());
                        }
                    }
                });

        return allSongs;

    }






}


