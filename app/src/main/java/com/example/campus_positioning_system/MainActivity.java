package com.example.campus_positioning_system;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import java.util.List;


public class MainActivity extends AppCompatActivity{
    private AppDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


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
}
