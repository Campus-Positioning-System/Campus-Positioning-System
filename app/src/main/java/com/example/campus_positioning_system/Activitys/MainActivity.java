package com.example.campus_positioning_system.Activitys;

// Standard Activity Library's

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

// Wifi and Compass Manager


// View

// Room Database
import androidx.fragment.app.FragmentManager;

import com.example.campus_positioning_system.Database.AppDatabase;
import com.example.campus_positioning_system.Database.NNObjectDao;
import com.example.campus_positioning_system.LocationNavigation.LocationControl;
import com.example.campus_positioning_system.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //Room Database Object
    private static AppDatabase db;
    private static Context thisContext;
    private BottomNavigationView navigationView;

    private static FragmentManager supportFragmentManager;

    //Sensor Activity Variables
    private SensorManager sensorManager;
    private Sensor accelerometer, magneticField;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    boolean isCopied = false;
    boolean isCopied2 = false;

    long lastUpdatedTime = 0;

    private static int angle;


    //Static height and width of the display
    public static int height;
    public static int width;
    public static int navigationBarHeight;
    public static int statusBarHeight;

    //Wifi Manager Variables
    private static WifiManager wifiManager;
    private static List<ScanResult> availableNetworks;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        System.out.println("On Create Main Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        thisContext = getApplicationContext();


        supportFragmentManager = getSupportFragmentManager();

        //------------------------------------------------------------------------------
        //Sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //------------------------------------------------------------------------------
        //Display height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels + getNavigationBarHeight();
        width = displayMetrics.widthPixels;
        navigationBarHeight = getNavigationBarHeight();
        statusBarHeight = getStatusBarHeight();
        //------------------------------------------------------------------------------
        //Wifi Scan Test
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        //------------------------------------------------------------------------------



        navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("Item is: " + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.nav_room_list:
                        switchActivities();
                        break;

                    case R.id.nav_settings:
                }
                return false;
            }
        });

        System.out.println("On Create Ende Main Activity");
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, RoomSelectionActivity.class);
        startActivity(switchActivityIntent);
    }

    //*********************************************
    //Methods for WifiScan new
    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void scanWifi() {
        WifiManager.ScanResultsCallback callback = new WifiManager.ScanResultsCallback() {
            @Override
            public void onScanResultsAvailable() {
                availableNetworks = wifiManager.getScanResults();
            }
        };
        callback.onScanResultsAvailable();
    }

    public static List<ScanResult> getAvailableNetworks() {
        return availableNetworks;
    }

    //*********************************************




    public static NNObjectDao getNNObjectDaoFromDB(){
        return db.k();
    }

    public static AppDatabase getDb(){
        return AppDatabase.getInstance();
    }

    public static FragmentManager getSupportFragmentManagerMain() {
        return supportFragmentManager;
    }

    public static Context mainContext() {
        return thisContext;
    }


    // Ab hier alles ehemalige Sensor Activity

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magneticField);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
            isCopied = true;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
            isCopied2 = true;
        }

        if (isCopied && isCopied2 && System.currentTimeMillis() - lastUpdatedTime > 250) {
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuthInRadians = orientationAngles[0];
            float azimuthInDegree = (float) Math.toDegrees(azimuthInRadians);

            lastUpdatedTime = System.currentTimeMillis();

            angle = (int) azimuthInDegree;
        }
    }

    public static int getAngle() {
        return angle;
    }

    //Function to get Navigation Bar Height
    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
