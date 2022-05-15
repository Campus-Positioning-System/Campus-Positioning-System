package com.example.campus_positioning_system;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Database;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity{
    private AppDatabase db;

    //Scaleable Map Background
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1f;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        imageView = findViewById(R.id.map);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


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
       // scannerThread.start();





    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            scaleFactor *= scaleGestureDetector.getScaleFactor();
            scaleFactor = Math.max(1f, Math.min(scaleFactor, 10.0f));
            imageView.setScaleX(scaleFactor);
            imageView.setScaleY(scaleFactor);
            return true;
        }
    }



}
