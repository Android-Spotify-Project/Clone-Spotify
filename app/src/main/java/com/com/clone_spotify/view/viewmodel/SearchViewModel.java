package com.com.clone_spotify.view.viewmodel;

import android.telecom.Call;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.com.clone_spotify.model.Song;
import com.com.clone_spotify.repository.SongRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    //repository 를 observe 하고있슴 => 구독이라고 말해봐

    private static final String TAG = "SearchViewModel";
    private SongRepository songRepository = new SongRepository();
    private LiveData<List<Song>> allSongs;


    public SearchViewModel(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public LiveData<List<Song>> observe(){ //구독하기
        return allSongs;
    }

    public void renewal(){ //데이터 갱신하기
        allSongs = songRepository.findAllSongs();
    }









}
