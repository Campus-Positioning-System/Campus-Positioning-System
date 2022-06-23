package com.example.campus_positioning_system.Database;

import android.net.wifi.ScanResult;
import android.widget.TextView;

import com.example.campus_positioning_system.LocationNavigation.LocationControl;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.Node;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DatabaseAccessThread extends Thread{
    private final List<ScanResult> availableNetworks;
    private final int scanAngle;
    private final TextView stockwerkView;


    private static Node currentPosition;

    public DatabaseAccessThread(List<ScanResult> wifiList, int scanAngle, TextView stockwerkView) {
        this.availableNetworks = wifiList;
        this.scanAngle = scanAngle;
        this.stockwerkView = stockwerkView;
    }


    @Override
    public void run() {
        System.out.println(availableNetworks.size());
        for(ScanResult s : availableNetworks) {
            System.out.print(s.BSSID + " ");
            System.out.println(s.level);
        }
        List<NNObject> nearestWifiList = availableNetworks.stream()
                .map(v -> new NNObject(v.BSSID.toUpperCase(), (float) v.level, null, scanAngle))
                .collect(Collectors.toList());

        //if(!() == null))
        //PathfindingControl.updateCurrentLocation(new LocationControl().locate(nearestWifiList));
        currentPosition = new LocationControl().locate(nearestWifiList);
        DrawingAssistant.setCurrentPosition(currentPosition);
        if(stockwerkView != null) {
            stockwerkView.setText(currentPosition.toString());
        }
    }
}
