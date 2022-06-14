package com.example.campus_positioning_system.Map;

import android.view.LayoutInflater;
import android.view.View;

import com.example.campus_positioning_system.LocationNavigation.WifiScanner;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;
import com.ortiz.touchview.TouchImageView;

public class DrawingAssistant extends Thread{
    private Node currentPosition;
    private Node destination;


    //x y z from our Coordinate System that we put on the map
    private int x = 124;
    private int y = 88;
    private int z = 5;

    //Map View
    TouchImageView mapView, dotView;

    WifiScanner wifiScanner;

    public DrawingAssistant(TouchImageView dotView, TouchImageView mapView, WifiScanner wifiScanner) {
        this.mapView = mapView;
        this.dotView = dotView;
        this.wifiScanner = wifiScanner;
    }

    public void setCurrentPosition(Node currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    //https://developer.android.com/training/animation/reposition-view

    public void run() {
        while(true) {
            System.out.println("In Drawing Assistant run()");
            System.out.println(mapView.getCurrentZoom());

            dotView.setZoom(mapView.getCurrentZoom());

            dotView.setZ(100);
            dotView.setX(100);
            dotView.setZ(100);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
