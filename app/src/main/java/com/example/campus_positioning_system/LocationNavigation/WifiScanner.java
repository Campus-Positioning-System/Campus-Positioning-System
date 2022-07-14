package com.example.campus_positioning_system.LocationNavigation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Database.DatabaseAccessThread;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.Node;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


/*
NearestWifiScanner
 */


public class WifiScanner extends Thread{

    private static int scanInterval = 5000;
    private final WifiManager wifiManager;

    private List<ScanResult> availableNetworks;
    private int scanAngle;
    private List<NNObject> nearestWifiList;


    private boolean shouldRun = true;

    private static TextView stockwerkView;

    public WifiScanner(Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Old code - don't need it
        if(!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        availableNetworks = wifiManager.getScanResults();
        scanAngle = MainActivity.getAngle();
    }

    public void setScanIntervall(int intervall){
        scanInterval = intervall;
    }

    public int adjustAngle(int angle){
       if(angle >= -45 && angle < 45)
            return 0;
        if(angle >= 45 && angle < 135)
            return 90;
        if(angle < -45 && angle >= -135)
            return 270;
        return 180;
    }


    public static void setStockwerkView (TextView view) {
        stockwerkView = view;
    }

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                //System.out.println("Scan did succeed in BroadcastReceiver");
                scanSuccess();
            } else {
                System.out.println("Scan did not succeed.");
            }
        }
    };

    private long lastUpdatedTime = 0;

    private void scanSuccess() {
        System.out.println("Scan did succeed");
        availableNetworks = wifiManager.getScanResults();
        scanAngle = MainActivity.getAngle();

        if(!availableNetworks.isEmpty() && System.currentTimeMillis() - lastUpdatedTime > 2000) {
            System.out.println(System.currentTimeMillis() - lastUpdatedTime);
            new DatabaseAccessThread(availableNetworks,adjustAngle(scanAngle),stockwerkView).start();
            lastUpdatedTime = System.currentTimeMillis();
        }
    }




    @Override
    public void run() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        MainActivity.mainContext().registerReceiver(wifiScanReceiver, intentFilter);

        while(shouldRun){
            boolean success = wifiManager.startScan();
            if (success) {
                //System.out.println("Scan did succeed in run()");
                scanSuccess();
            } else {
                System.out.println("Scan did not succeed.");
            }
            try {
                Thread.sleep(scanInterval);
            } catch (InterruptedException e) {
            }
        }
    }
}
