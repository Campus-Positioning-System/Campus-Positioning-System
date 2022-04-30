package com.example.campus_positioning_system;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


/*  Was?
    Eine Klasse welche die möglichen Wifi Verbindungen scant und diese Liste zur Verfügung stellt

 */


public class WifiScanner extends AppCompatActivity {

    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public List<ScanResult> getList() {
        List<ScanResult> availableNetworks = wifiManager.getScanResults();
        return availableNetworks;
    }
}
