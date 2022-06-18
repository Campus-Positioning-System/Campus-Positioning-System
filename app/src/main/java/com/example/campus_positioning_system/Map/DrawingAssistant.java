package com.example.campus_positioning_system.Map;

import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
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

    private int height;
    private int width;
    private boolean setHW = false;

    //Map View
    private TouchImageView mapView, dotView;

    private WifiScanner wifiScanner;

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

    // https://developer.android.com/training/animation/reposition-view


    @Override
    public void run() {
        Mover dotMover;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true) {
            if(!setHW && mapView.getHeight()!=0.0) {
                this.height = mapView.getHeight();
                this.width = mapView.getWidth();
            }

            dotView.setZoom(3-mapView.getCurrentZoom());
            System.out.println(mapView.getScrollPosition());

            dotMover = new Mover("DotMover");
            dotMover.setView(dotView);
            PointF pointF = mapView.getScrollPosition();
            System.out.println(pointF.x + " " + height);
            dotMover.setNewPosition(pointF.x*width,pointF.y*height);
            dotMover.start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
