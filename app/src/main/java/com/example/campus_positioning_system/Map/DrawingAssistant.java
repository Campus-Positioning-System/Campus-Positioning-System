package com.example.campus_positioning_system.Map;

import android.graphics.PointF;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.LocationNavigation.WifiScanner;
import com.example.campus_positioning_system.Node;
import com.ortiz.touchview.TouchImageView;

public class DrawingAssistant extends Thread{
    private Node currentPosition;
    private Node destination;


    //x y z from our Coordinate System that we put on the map
    private final int x = 124;
    private final int y = 88;
    private final int z = 5;

    //Height and Width of our View's
    private int dotHeight;
    private int dotWidth;
    private int mapHeight;
    private int mapWidth;

    //Set Height and View -> Only needs to be done Once -> is in while(true)
    //because its depending on when the Views got inflated and we need to wait for that to happen
    //so Width and Height is not null
    private boolean setHW = false;

    //View's
    private final TouchImageView mapView;
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
                int height = MainActivity.height;
                int width = MainActivity.width;

                this.mapHeight = mapView.getHeight();
                this.mapWidth = mapView.getWidth();

                this.dotHeight = dotView.getHeight();
                this.dotWidth = dotView.getWidth();

                int navigationBarHeight = MainActivity.navigationBarHeight;
                int statusBarHeight = MainActivity.statusBarHeight;
                System.out.println("Drawing assistant received Device height: " + height + " and width: " + width);
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


        while(true) {
            System.out.println("----------------------------------------------------------------------");
            dotView.setZoom((float) (2-mapView.getCurrentZoom()));


            //The getScrollPosition is dependant on the View.
            //So x:0 y:0 would be the left top corner of the VIEW
            PointF mapViewCenter = mapView.getScrollPosition();
            PointF dotViewCenter = dotView.getScrollPosition();

            //Calculate Center:
            float centerX = mapConverter.getRealCenter(mapViewCenter.x, (float) 0.0);
            float centerY = mapConverter.getRealCenter(mapViewCenter.y, (float) 1.65);

            System.out.println("Center of mapView(x,y): " + centerX + " " + centerY);
            System.out.println(mapConverter.getCurrentZoom());


            newX = centerX * (mapWidth) - (dotWidth/(float) 2);
            newY = centerY * (mapHeight) - (dotHeight/(float)2);

            dotMover.setNewPosition(newX,newY);
            dotMover.animationStart();

            System.out.println(" ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
