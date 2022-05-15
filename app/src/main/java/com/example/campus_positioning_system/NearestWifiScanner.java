package com.example.campus_positioning_system;


import android.content.Context;
import android.hardware.SensorEventListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;
import java.util.stream.Collectors;


/*  NearestWifiScanner
 */


public class NearestWifiScanner implements Runnable{

    private final WifiManager wifiManager;

    private List<ScanResult> availableNetworks;
    private int scanAngle;
    private List<NNObject> nearestWifiList;
    private SensorEventListener listener;

    private boolean shouldRun = true;

    public NearestWifiScanner(Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Old code - don't need it
        if(!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        availableNetworks = wifiManager.getScanResults();
        scanAngle = SensorActivity.getAngle();
    }

    public synchronized List<ScanResult> getWifiList() {
        return this.availableNetworks;
    }

    public synchronized List<NNObject> getNearestWifiList() {
        return null;
    }


    @Override
    public void run() {
        while(shouldRun){
            wifiManager.startScan();
            availableNetworks = wifiManager.getScanResults();
            scanAngle = SensorActivity.getAngle();

            nearestWifiList = availableNetworks.stream()
                    .map(v-> new NNObject(v.BSSID,(float)v.level,null,scanAngle))
                    .collect(Collectors.toList());

            List<String> relevantAdresses = availableNetworks.stream()
                    .map(v->v.BSSID)
                    .collect(Collectors.toList());

            if(!availableNetworks.isEmpty()) {
                System.out.println(availableNetworks.get(0).BSSID);
                System.out.println(availableNetworks.get(0).SSID);
                System.out.println(availableNetworks.get(0).level);
                System.out.println(scanAngle);
            }

            if(!(new PathfindingControl().updateCurrentLocation(new LocationControl().locate(nearestWifiList, relevantAdresses)) == null))
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
        }
    }
}
