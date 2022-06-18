package com.example.campus_positioning_system.Activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_positioning_system.Database.NNObjectDao;
import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.RoomList.RoomListViewHolder;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderAdapter;

import java.util.List;

public class RoomSelectionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_selection_activity);
        System.out.println("On Create RoomSelect Activity");

        RecyclerView list = findViewById(R.id.room_list);
        System.out.println("Retrieving DAO");
        System.out.println("Converting to list");
        List<NNObject> rooms = MainActivity.getDb().k().getAllData();
        System.out.println("Conversion successful");
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        RecyclerView.Adapter<RoomListViewHolder> mAdapter = new RoomListViewHolderAdapter(this, rooms);
        System.out.println("Setting Adapter");
        list.setAdapter(mAdapter);
        System.out.println("Adapter is now " + list.getAdapter());

    }
    @Override
    public void onBackPressed()
    {
        System.out.println("User wants to go back from Room list");

        NavHostFragment navHostFragment = (NavHostFragment) MainActivity.getSupportFragmentManagerMain().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.roomSelectionFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        MainActivity.setOnlyNavigateOnceTrue();

        System.out.println("Navigating from Room List back to Main");
    }

}
