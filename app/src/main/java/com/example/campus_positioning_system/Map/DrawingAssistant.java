package com.example.campus_positioning_system.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PointF;
import android.view.View;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.LocationNavigation.WifiScanner;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;
import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DrawingAssistant extends Thread{
    private static Node currentPosition;
    private static List<Node> path = new ArrayList<>();

    private static boolean pathDrawn = false;

    //Height and Width of our View's
    private static int dotHeight;
    private static int dotWidth;
    private static int mapHeight;
    private static int mapWidth;
    private static int displayWidth;
    private static int displayHeight;

    //Set Height and View -> Only needs to be done Once -> is in while(true)
    //because its depending on when the Views got inflated and we need to wait for that to happen
    //so Width and Height is not null
    private boolean setHW = false;

    //View's
    private static TouchImageView mapView = null;
    private static int currentMap;

    private final TouchImageView dotView;

    //Map Converter Node to Px on Screen
    private static MapConverter mapConverter;

    public DrawingAssistant(TouchImageView dotView, TouchImageView mapView, WifiScanner wifiScanner) {
        DrawingAssistant.mapView = mapView;
        this.dotView = dotView;
        currentPosition = new Node("PointZero",62,44,1);
    }

    public static void setCurrentPosition(Node currentPosition1) {
        currentPosition = currentPosition1;
        // Hier fehlt noch ein Argument, dass die Image Source nur geaendert werden kann, wenn
        // der Path nicht angezeigt werden muss.
        if(currentPosition.getZ() == 0) {
           // mapView.setImageResource(R.drawable.eg);
            currentMap = R.drawable.eg;
        } else if(currentPosition.getZ() == 1) {
           // mapView.setImageResource(R.drawable.og1example);
            currentMap = R.drawable.og1example;
        } else if(currentPosition.getZ() == 2) {
            //mapView.setImageResource(R.drawable.og2);
            currentMap = R.drawable.og2;
        } else if(currentPosition.getZ() == 3) {
           // mapView.setImageResource(R.drawable.og345);
            currentMap = R.drawable.og345;
        }
    }

    public static void setPathToDestination(List<Node> pathToDestination) {
        path =  new ArrayList<>(pathToDestination);
    }

    // https://developer.android.com/training/animation/reposition-view

    private TouchImageView testView;

    public void setTest(TouchImageView view) {
        this.testView = view;
    }

    public void drawPath() {
        Bitmap mapBitmap = BitmapFactory.decodeResource(MainActivity.mainContext().getResources(), R.drawable.og1example);

        float einx = (float) mapBitmap.getWidth()/124f;
        float einy = (float) mapBitmap.getHeight()/88f;

        Bitmap mutableBitmap = mapBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(15);

        List<MapPosition> mapPositions = new LinkedList<>();

        for(int i = 0; i< path.size(); i++) {
            System.out.println(path.getClass());
            Node n = path.get(i);
            System.out.println("Da: " + n.getX() + " " + n.getY());
            MapPosition mapPosition = new MapPosition();
            mapPosition.setX(n.getX()*einx);
            mapPosition.setY(n.getY()*einy);
            mapPositions.add(mapPosition);
        }

        System.out.println(mapPositions.size());
        for(int i=0;i<(mapPositions.size()-1); i++) {
            System.out.println("Hier: " +  mapPositions.get(i).getX() + " " + mapPositions.get(i).getY());
            System.out.println("Hier: " +  mapPositions.get(i+1).getX() + " " + mapPositions.get(i+1).getY());
            canvas.drawLine(mapPositions.get(i).getX(),mapPositions.get(i).getY(),mapPositions.get(i+1).getX(),mapPositions.get(i+1).getY(),paint);
        }
        pathDrawn = true;
        mapView.setImageBitmap(mutableBitmap);
    }

    public void removePath() {
        mapView.setImageResource(R.drawable.og1example);
    }

    @Override
    public void run() {
        float newX = (float) 0;
        float newY = (float) 0;
        Mover dotMover= new Mover("DotMover",newX,newY);

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

                mapConverter = new MapConverter(mapHeight, mapWidth, dotHeight, dotWidth, mapView);
                setHW = true;
            }
        }

        while(true) {
            //System.out.println("----------------------------------------------------------------------");
            dotView.setZoom((float) (2-mapView.getCurrentZoom()));

            if(!path.isEmpty() && !pathDrawn) {
                System.out.println("Dada");
                mapView.setZoom(1.0f);
                drawPath();
            }

            MapPosition position = mapConverter.convertNode(currentPosition);

            dotMover.setNewPosition(position.getX(), position.getY());
            dotMover.animationStart();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
