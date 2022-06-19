package com.example.campus_positioning_system.Map;

import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;

import com.example.campus_positioning_system.Activitys.MainActivity;
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

    private int dotHeight;
    private int dotWidth;

    private int mapHeight;
    private int mapWidth;

    private int navigationBarHeight;
    private int statusBarHeight;

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
        Float lastX = (float) 0;
        Float lastY = (float) 0;

        dotMover = new Mover("DotMover",lastX,lastY);
        dotMover.setView(dotView);
        dotMover.start();



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while(true) {
            if(!setHW && mapView.getHeight() != 0.0) {
                this.height = MainActivity.height;
                this.width = MainActivity.width;

                this.mapHeight = mapView.getHeight();
                this.mapWidth = mapView.getWidth();

                this.dotHeight = dotView.getHeight();
                this.dotWidth = dotView.getWidth();

                this.navigationBarHeight = MainActivity.navigationBarHeight;
                this.statusBarHeight = MainActivity.statusBarHeight;

                System.out.println("Drawing assistant received Device height: " + height + " and width: " + width);
                System.out.println("Dot height: " + dotHeight + " and width: " + dotWidth);
                System.out.println("Map height: " + mapHeight + " and width: " + mapWidth);
                System.out.println("Navigation Bar height: " + navigationBarHeight);
                System.out.println("Status Bar height: " + statusBarHeight);
                setHW = true;
            }

            dotView.setZoom((float) (2-mapView.getCurrentZoom()));



            //The getScrollPosition is dependant on the View.
            //So x:0 y:0 would be the left top corner of the VIEW
            PointF mapViewCenter = mapView.getScrollPosition();
            PointF dotViewCenter = dotView.getScrollPosition();
            System.out.println("Center of mapView(x,y): " + mapViewCenter.x + " " + mapViewCenter.y);
            System.out.println("Center of dotView(x,y): " + dotViewCenter.x + " " + dotViewCenter.y);

            lastX = (1-mapViewCenter.x) * mapWidth - dotHeight/2;
            lastY = (1-mapViewCenter.y) * mapHeight - dotHeight/2;

            dotMover.setNewPosition(lastX,lastY);
            dotMover.animationStart();


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
