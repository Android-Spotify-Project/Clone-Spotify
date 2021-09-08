package com.com.clone_spotify.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentContainerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.RequestManager;
import com.com.clone_spotify.R;
import com.com.clone_spotify.SpotifyApplication;
import com.com.clone_spotify.client.MediaBrowserHelper;
import com.com.clone_spotify.client.MediaBrowserHelperCallback;
import com.com.clone_spotify.model.Library;
import com.com.clone_spotify.model.SongDto;
import com.com.clone_spotify.service.MusicService;
import com.com.clone_spotify.util.MainActivityFragmentManager;
import com.com.clone_spotify.util.MyPreferenceManager;
import com.com.clone_spotify.view.fragments.HomeFragment;
import com.com.clone_spotify.view.fragments.LibraryFragment;
import com.com.clone_spotify.view.fragments.MediaControllerFragment;
import com.com.clone_spotify.view.fragments.SearchFragment;
import com.com.clone_spotify.view.fragments.SearchMenuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static com.com.clone_spotify.util.Constants.MEDIA_QUEUE_POSITION;
import static com.com.clone_spotify.util.Constants.QUEUE_NEW_PLAYLIST;
import static com.com.clone_spotify.util.Constants.SEEK_BAR_MAX;
import static com.com.clone_spotify.util.Constants.SEEK_BAR_PROGRESS;


public class MainActivity extends CustomAppBarActivity  implements InitMainActivity, MediaBrowserHelperCallback {

    private ProgressBar mProgressBar;
    private static final String TAG = "MainActivity2";

    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    final HomeFragment homeFragment = HomeFragment.newInstance();
    final SearchMenuFragment searchMenuFragment = SearchMenuFragment.newInstance();
    final SearchFragment searchFragment = SearchFragment.newInstance();
    final LibraryFragment libraryFragment = LibraryFragment.newInstance();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;


    //appbar
    private Toolbar toolbar;
    // musicService
    private MediaBrowserHelper mMediaBrowserHelper;
    private SpotifyApplication mMyApplication;
    private MyPreferenceManager mMyPrefManager;
    private boolean mIsPlaying;
    private SeekBarBroadcastReceiver mSeekbarBroadcastReceiver;
    private UpdateUIBroadcastReceiver mUpdateUIBroadcastReceiver;
    private boolean mOnAppOpen;
    private boolean mWasConfigurationChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: 온 크레이트 실행 됨");
        //bottomNavigationView.setSelectedItemId(R.id.bottom_nav);

        init();
        initLr();

        //fragment 초기화면 설정 ==> initLr 이 먼저 실행되고 또 newInstance 하기때문에 2번실행이됨. 그래서 주석처리 함.
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.fragmentContainer, HomeFragment.newInstance()).commit();
        
    }

    /*내비게이션 화면이동=========================================================================================*/

    public void init(){
        Log.d(TAG, "init: ");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.toolbarMain);
        mProgressBar = findViewById(R.id.progress_bar);

        mMyApplication = SpotifyApplication.getInstance();
        mMyPrefManager = new MyPreferenceManager(this);
        mMediaBrowserHelper = new MediaBrowserHelper(this, MusicService.class);
        mMediaBrowserHelper.setMediaBrowserHelperCallback(this);

        fm.beginTransaction().add(R.id.fragmentContainer, libraryFragment, "3").hide(libraryFragment).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, searchFragment, "4").hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, searchMenuFragment, "2").hide(searchMenuFragment).commit();
        fm.beginTransaction().add(R.id.fragmentContainer, homeFragment, "1").commit();

    }

    public void initLr(){
        //네비게이션 누르면 각각 화면 뿌리기
        bottomNavigationView.setOnItemSelectedListener(item ->  {
                // Fragment() 매개변수 제거
                switch (item.getItemId()) {
                    case R.id.library:
                        Log.d(TAG, "initLr: 라이브러리 클릭");
                        fm.beginTransaction().hide(active).show(libraryFragment).commit();
                        active = libraryFragment;
                        break;

                    case R.id.searchFragment:
                        Log.d(TAG, "initLr: 서치 프레그먼트 클릭 ");
                        fm.beginTransaction().hide(active).show(searchMenuFragment).commit();
                        active = searchMenuFragment;
                        break;

                    case R.id.homeFragment:
                        Log.d(TAG, "initLr: 홈프레그먼트 클릭");
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
                        break;
                }
                return true;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {
            Toast.makeText(MainActivity.this, "Reselected", Toast.LENGTH_SHORT).show();
        });

    }


    public void replaceFragment(Fragment fragment){
        Log.d(TAG, "replaceFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();

    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    // mediaController 연결 로직 시작
    @Override
    public void onMediaControllerConnected(MediaControllerCompat mediaController) {
        getMediaControllerFragment().getMediaSeekBar().setMediaController(mediaController);
    }

    private MediaControllerFragment getMediaControllerFragment(){
        MediaControllerFragment mediaControllerFragment = (MediaControllerFragment)getSupportFragmentManager()
                .findFragmentById(R.id.bottom_media_controller);
        if(mediaControllerFragment != null){
            return mediaControllerFragment;
        }
        return null;
    }
    // mediaController 연결 로직 끝

    private LibraryFragment getLibraryFragment(){
        LibraryFragment libraryFragment = (LibraryFragment)getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.fragment_library));
        if(libraryFragment != null){
            return libraryFragment;
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mSeekbarBroadcastReceiver != null){
            unregisterReceiver(mSeekbarBroadcastReceiver);
        }
        if(mUpdateUIBroadcastReceiver != null){
            unregisterReceiver(mUpdateUIBroadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSeekBarBroadcastReceiver();
        initUpdateUIBroadcastReceiver();
    }

    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        Log.d(TAG, "onMetadataChanged: called");
        if(metadata == null){
            return;
        }

        // Do stuff with new Metadata
        getMediaControllerFragment().setMediaInfo(metadata);
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        Log.d(TAG, "onPlaybackStateChanged: called.");
        mIsPlaying = state != null &&
                state.getState() == PlaybackStateCompat.STATE_PLAYING;

        // update UI
        if(getMediaControllerFragment() != null){
            getMediaControllerFragment().setIsPlaying(mIsPlaying);
        }
    }


//    // progressBar 작동 메서드 시작
//    @Override
//    public void hideProgressBar() {
//        mProgressBar.setVisibility(View.GONE);
//    }

//    @Override
//    public void showProgressBar() {
//        mProgressBar.setVisibility(View.VISIBLE);
//    }
    // progressBar 작동 메서드 끝


    @Override
    public void playPause() {
        if(mOnAppOpen){
            if (mIsPlaying) {
                mMediaBrowserHelper.getTransportControls().pause();
            }
            else {
                mMediaBrowserHelper.getTransportControls().play();

            }
        } else {
            if(!getMyPreferenceManager().getLastPlayedMedia().equals("")){
                onMediaSelected(
                        mMyApplication.getMediaItem(getMyPreferenceManager().getLastPlayedMedia()),
                        getMyPreferenceManager().getQueuePosition());
            } else {
                Toast.makeText(this, "select something to play", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!getMyPreferenceManager().getLastPlayedMedia().equals("")){
            prepareLastPlayedMedia();
        }
        else{
            mMediaBrowserHelper.onStart(mWasConfigurationChange);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaBrowserHelper.onStop();
        getMediaControllerFragment().getMediaSeekBar().disconnectController();
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mWasConfigurationChange = true;
    }

    @Override
    public SpotifyApplication getMyApplicationInstance() {
        return mMyApplication;
    }

    @Override
    public void onMediaSelected(MediaMetadataCompat mediaItem, int queuePosition) {
        if(mediaItem != null){
            Log.d(TAG, "onMediaSelected: CALLED: " + mediaItem.getDescription().getMediaId());

            String currentPlaySongId = getMyPreferenceManager().getLastPlayedMedia();

            Bundle bundle = new Bundle();
            bundle.putInt(MEDIA_QUEUE_POSITION, queuePosition);
            if(mediaItem.getDescription().getMediaId().equals(currentPlaySongId)){
                mMediaBrowserHelper.getTransportControls().playFromMediaId(mediaItem.getDescription().getMediaId(), bundle);
            }
            else{
                bundle.putBoolean(QUEUE_NEW_PLAYLIST, true); // let the player know this is a new playlist
                mMediaBrowserHelper.subscribeToNewSong(mediaItem.getDescription().getMediaId(), mediaItem.getDescription().getMediaId());
                mMediaBrowserHelper.getTransportControls().playFromMediaId(mediaItem.getDescription().getMediaId(), bundle);
            }

            mOnAppOpen = true;
        }
        else{
            Toast.makeText(this, "select something to play", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public MyPreferenceManager getMyPreferenceManager() {
        return mMyPrefManager;
    }

    // 브로드캐스팅 UI 연동====
    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String newMediaId = intent.getStringExtra(getString(R.string.broadcast_new_media_id));
            Log.d(TAG, "onReceive: CALLED: " + newMediaId);
            if (getLibraryFragment() != null) {
                Log.d(TAG, "onReceive: " + mMyApplication.getMediaItem(newMediaId).getDescription().getMediaId());
                getLibraryFragment().updateUI(mMyApplication.getMediaItem(newMediaId));
            }
        }
    }

    private void initUpdateUIBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.broadcast_update_ui));
        mUpdateUIBroadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(mUpdateUIBroadcastReceiver, intentFilter);
    }

    // 마지막에 튼 노래 연동
    private void prepareLastPlayedMedia() {
//        showProgressBar();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Library");

        final List<MediaMetadataCompat> mediaItems = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: 데이터 받아옴");
                Log.d(TAG, "onDataChange: 클리어 완료");
                // 반복문으로 데이터 list 추출함
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    SongDto song = snapshot.getValue(SongDto.class);
                    MediaMetadataCompat mediaItem = addToMediaList(song);

                    mediaItems.add(mediaItem);
                    if (mediaItem.getDescription().getMediaId().equals(getMyPreferenceManager().getLastPlayedMedia())) {
                        getMediaControllerFragment().setMediaInfo(mediaItem);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError e) {
                Log.e(TAG, "onCancelled: ", e.toException());
            }
        });
        onFinishedGettingPreviousSessionData(mediaItems);
    }

    private void onFinishedGettingPreviousSessionData(List<MediaMetadataCompat> mediaItems){
        mMyApplication.setMediaItems(mediaItems);
        mMediaBrowserHelper.onStart(mWasConfigurationChange);
//        hideProgressBar();
    }
    // 마지막에 튼 노래 연동 로직 끝

    private MediaMetadataCompat addToMediaList(SongDto songDto){

        MediaMetadataCompat media = new MediaMetadataCompat.Builder()

                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, songDto.getMediaId())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songDto.getArtistName())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songDto.getSongTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, songDto.getSongUrl())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, songDto.getSongTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, songDto.getAlbumImg())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, songDto.getArtistName())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, getMyPreferenceManager().getLastPlayedArtistImage())
                .build();

        return media;
    }
    // seekBar 브로드캐스트 리시버 시작
    private class SeekBarBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long seekProgress = intent.getLongExtra(SEEK_BAR_PROGRESS, 0);
            long seekMax = intent.getLongExtra(SEEK_BAR_MAX, 0);
            if(!getMediaControllerFragment().getMediaSeekBar().isTracking()){
                getMediaControllerFragment().getMediaSeekBar().setProgress((int)seekProgress);
                getMediaControllerFragment().getMediaSeekBar().setMax((int)seekMax);
            }
        }
    }

    private void initSeekBarBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.broadcast_seekbar_update));
        mSeekbarBroadcastReceiver = new SeekBarBroadcastReceiver();
        registerReceiver(mSeekbarBroadcastReceiver, intentFilter);
    }
    // seekBar 브로드캐스트 리시버 끝
}