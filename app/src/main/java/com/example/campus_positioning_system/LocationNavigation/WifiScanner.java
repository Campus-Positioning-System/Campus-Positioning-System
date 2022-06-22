package com.example.campus_positioning_system.LocationNavigation;


import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.Node;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


/*  NearestWifiScanner
 */


public class WifiScanner extends Thread{

    private static int scanInterval = 10000;
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

    public synchronized List<ScanResult> getWifiList() {
        return this.availableNetworks;
    }

    public synchronized List<NNObject> getNearestWifiList() {
        return null;
    }

    public void setScanIntervall(int intervall){
        this.scanInterval = intervall;
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


    public static void setStockwerkView (TextView view) {
        stockwerkView = view;
    }




    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void run() {
        while(shouldRun){
            wifiManager.startScan();
            availableNetworks = wifiManager.getScanResults();

            scanAngle = MainActivity.getAngle();

            if(!availableNetworks.isEmpty()) {
                System.out.println("Scan hat funktioniert");
                System.out.println(availableNetworks.size());
                for(ScanResult s : availableNetworks) {
                    System.out.print(s.BSSID + " ");
                    System.out.println(s.level);
                }
                nearestWifiList = availableNetworks.stream()
                        .map(v-> new NNObject(v.BSSID,(float)v.level,null,this.adjustAngle(scanAngle)))
                        .collect(Collectors.toList());

                List<String> relevantAdresses = availableNetworks.stream()
                        .map(v->v.BSSID)
                        .distinct()
                        .collect(Collectors.toList());

                //if(!() == null))
                //PathfindingControl.updateCurrentLocation(new LocationControl().locate(nearestWifiList));
                Node currentPosition = new LocationControl().locate(nearestWifiList);
                DrawingAssistant.setCurrentPosition(currentPosition);
                if(stockwerkView != null) {
                    stockwerkView.setText(currentPosition.toString());
                }

            }
            try {
                Thread.sleep(scanInterval);
            } catch (InterruptedException e) {
            }
        }
    }
}
