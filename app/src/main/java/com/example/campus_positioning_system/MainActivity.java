package com.example.campus_positioning_system;

// Standard Activity Library's

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

// Wifi and Compass Manager


// View

// Room Database
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity{
    //Room Database Object
    private static AppDatabase db;
    private Context thisContext;
    private BottomNavigationView navigationView;

    private static Boolean onlyNavigateOnce = true;
    private static FragmentManager supportFragmentManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("On Create Main Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        supportFragmentManager = getSupportFragmentManager();


        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_room_list:
                    if(onlyNavigateOnce) {
                        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
                        NavController navController = navHostFragment.getNavController();
                        navController.navigate(R.id.roomSelectionFragment);
                        onlyNavigateOnce = false;
                    }
                    /*
                    Intent intent = new Intent(thisContext, RoomSelectionActivity.class);
                    startActivity(intent);
                     */
            }
            return false;
        });



        System.out.println("On Create Ende Main Activity");
    }

    public static NNObjectDao getNNObjectDaoFromDB(){
        return db.getNNObjectDao();
    }

    public static void setOnlyNavigateOnceTrue() {
        onlyNavigateOnce = true;
    }

    public static FragmentManager getSupportFragmentManagerMain() {
        return supportFragmentManager;
    }


}

/*
        DrawingAssistant drawingAssistant = new DrawingAssistant();

        Intent wifiRights = new Intent(Settings.Panel.ACTION_WIFI);
        startActivity(wifiRights);


        new Thread(() -> db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase.class).build()).start();

        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);

        WifiScanner nearestWifiScanner = new WifiScanner(getApplicationContext());
        Thread scannerThread = new Thread(nearestWifiScanner);
       // scannerThread.start();
        */
