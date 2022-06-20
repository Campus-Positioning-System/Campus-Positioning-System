package com.example.campus_positioning_system.Map;

import android.graphics.PointF;
import android.view.View;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.LocationNavigation.WifiScanner;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;
import com.ortiz.touchview.TouchImageView;


public class DrawingAssistant extends Thread{
    private Node currentPosition;
    private Node destination;




    //Height and Width of our View's
    private int dotHeight;
    private int dotWidth;
    private int mapHeight;
    private int mapWidth;
    private int displayWidth;
    private int displayHeight;

    //Set Height and View -> Only needs to be done Once -> is in while(true)
    //because its depending on when the Views got inflated and we need to wait for that to happen
    //so Width and Height is not null
    private boolean setHW = false;

    //View's
    private TouchImageView mapView;
    private final TouchImageView dotView;

    //Map Converter Node to Px on Screen
    private MapConverter mapConverter;

    public DrawingAssistant(TouchImageView dotView, TouchImageView mapView, WifiScanner wifiScanner) {
        this.mapView = mapView;
        this.dotView = dotView;
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
        float newX = (float) 0;
        float newY = (float) 0;

        dotMover = new Mover("DotMover",newX,newY);
        dotMover.setView(dotView);
        dotMover.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!setHW) {
            if(mapView.getHeight() != 0.0) {
                this.displayHeight = MainActivity.height;
                this.displayWidth = MainActivity.width;

                this.mapHeight = mapView.getHeight();
                this.mapWidth = mapView.getWidth();

                this.dotHeight = dotView.getHeight();
                this.dotWidth = dotView.getWidth();

                int navigationBarHeight = MainActivity.navigationBarHeight;
                int statusBarHeight = MainActivity.statusBarHeight;
                System.out.println("Drawing assistant received Device height: " + displayHeight + " and width: " + displayWidth);
                System.out.println("Dot height: " + dotHeight + " and width: " + dotWidth);
                System.out.println("Map height: " + mapHeight + " and width: " + mapWidth);
                System.out.println("Navigation Bar height: " + navigationBarHeight);
                System.out.println("Status Bar height: " + statusBarHeight);

                mapView.setMaxHeight(mapHeight);
                mapView.setMaxWidth(mapWidth);

                mapConverter = new MapConverter(mapHeight,mapWidth,mapView);
                setHW = true;
            }
        }

        Node pos11 = new Node( "",62,40,1);

        if(pos11.getZ() == 0) {
            mapView.setImageResource(R.drawable.eg);
        } else if(pos11.getZ() == 1) {
            mapView.setImageResource(R.drawable.og1example);
        } else if(pos11.getZ() == 2) {
            mapView.setImageResource(R.drawable.og2);
        } else if(pos11.getZ() == 3) {
            mapView.setImageResource(R.drawable.og345);
        }

        while(true) {
            System.out.println("----------------------------------------------------------------------");
            dotView.setZoom((float) (2-mapView.getCurrentZoom()));

            MapPosition position = mapConverter.convertNode(pos11);
            newX = position.getX() * (mapWidth) - (dotWidth/(float)2);
            newY = position.getY() * (mapHeight) - (dotHeight/(float)2);

            dotMover.setNewPosition(newX,newY);
            dotMover.animationStart();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
