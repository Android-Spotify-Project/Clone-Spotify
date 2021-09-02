package com.com.clone_spotify.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.com.clone_spotify.R;
import com.com.clone_spotify.adapters.HomeAdapter;
import com.com.clone_spotify.model.Home;
import com.com.clone_spotify.view.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private RecyclerView reHome;
    private RecyclerView.Adapter hAdapter;
    private RecyclerView.LayoutManager hLayoutManager,hLayoutManager2;
    private ArrayList<Home> hArrayList;
    private FirebaseDatabase hDatabase;
    private DatabaseReference hDatabaseReference;

    private MainActivity mContext;


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main, menu);
    }

    public HomeFragment(MainActivity mContext){
        this.mContext = mContext;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");


        //화면마다 appbar 설정
        ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
        ab.setTitle("clone-spotify");
        ab.setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home,container, false);
        Log.d(TAG, "onCreateView: ");
        reHome = rootView.findViewById(R.id.reHome);
        reHome.setHasFixedSize(true);
        hLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        reHome.setLayoutManager(hLayoutManager);
        hArrayList = new ArrayList<>();

        hDatabase = FirebaseDatabase.getInstance();

        hDatabaseReference = hDatabase.getReference("Home");
        hDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot hDataSnapshot) {
                hArrayList.clear();
                for (DataSnapshot hSnapshot : hDataSnapshot.getChildren()){
                    Home home = hSnapshot.getValue(Home.class);
                    hArrayList.add(home);
                }
                hAdapter.notifyDataSetChanged();
                Log.d(TAG, "onDataChange: 데이터 받아옴");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        hAdapter = new HomeAdapter(hArrayList, getContext());
        reHome.setAdapter(hAdapter);

        return rootView;
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }


}