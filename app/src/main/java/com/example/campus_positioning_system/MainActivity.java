package com.example.campus_positioning_system;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);

        NearestWifiScanner nearestWifiScanner = new NearestWifiScanner(getApplicationContext());
        Thread scannerThread = new Thread(nearestWifiScanner);
        scannerThread.start();
    }

}
