package com.example.campus_positioning_system;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    private AppDatabase db;

    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);


        new Thread(new Runnable() {
            @Override
            public void run() {
                db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase.class).build();
            }
        }).start();

        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);

        NearestWifiScanner nearestWifiScanner = new NearestWifiScanner(getApplicationContext());
        Thread scannerThread = new Thread(nearestWifiScanner);
        scannerThread.start();





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        System.out.println("Selected Item was " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_favorites:
                RecyclerView fav_list = findViewById(R.id.favorites_list);
                if(fav_list.getVisibility() == View.VISIBLE)
                    fav_list.setVisibility(View.INVISIBLE);
                else
                    fav_list.setVisibility(View.VISIBLE);

                return true;

            case R.id.nav_room_list:

                RecyclerView room_list = findViewById(R.id.room_list);
                if(room_list.getVisibility() == View.VISIBLE)
                    room_list.setVisibility(View.INVISIBLE);
                else
                    room_list.setVisibility(View.VISIBLE);

                return true;

            case R.id.nav_settings:

                return true;


        }
        return false;
    }



}
