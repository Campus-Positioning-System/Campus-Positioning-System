package com.example.campus_positioning_system.LocationNavigation;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.NNObject;

import java.util.List;
import java.util.stream.Collectors;


/*  NearestWifiScanner
 */


public class WifiScanner extends Thread{

    private static int scanIntervall = 500;
    private final WifiManager wifiManager;

    private List<ScanResult> availableNetworks;
    private int scanAngle;
    private List<NNObject> nearestWifiList;


    private boolean shouldRun = true;


    public WifiScanner(Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Old code - don't need it
        if(!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        availableNetworks = wifiManager.getScanResults();
        scanAngle = MainActivity.getAngle();
    }

    public synchronized List<ScanResult> getWifiList() {
        return this.availableNetworks;
    }

    public synchronized List<NNObject> getNearestWifiList() {
        return null;
    }

    public void setScanIntervall(int intervall){
        this.scanIntervall = intervall;
    }

    public int adjustAngle(int angle){
        if(angle <= 45 && angle > 135)
            return 90;
        if(angle <= 135 && angle > 215)
            return 180;
        if(angle <= 215 && angle > 315)
            return 270;
        return 0;
    }





    @Override
    public void run() {
        while(shouldRun){
            wifiManager.startScan();
            availableNetworks = wifiManager.getScanResults();
            scanAngle = MainActivity.getAngle();

            nearestWifiList = availableNetworks.stream()
                    .map(v-> new NNObject(v.BSSID,(float)v.level,null,this.adjustAngle(scanAngle)))
                    .collect(Collectors.toList());

            List<String> relevantAdresses = availableNetworks.stream()
                    .map(v->v.BSSID)
                    .distinct()
                    .collect(Collectors.toList());

            //if(!() == null))
            //PathfindingControl.updateCurrentLocation(new LocationControl().locate(nearestWifiList));
            DrawingAssistant.setCurrentPosition(new LocationControl().locate(nearestWifiList));

            try {
                Thread.sleep(scanIntervall);
            } catch (InterruptedException e) {
            }
        }
    }
}
