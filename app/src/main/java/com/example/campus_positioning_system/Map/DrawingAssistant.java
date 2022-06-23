package com.example.campus_positioning_system.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Fragments.MainFragment;
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

    //All Map Bitmaps from Original Maps
    private static final List<Bitmap> allBitmapsOriginal = new LinkedList<>();
    private List<Bitmap> bitmapsWithPath;

    private final static int MAP_EG = R.drawable.eg;
    private final static int MAP_OG1 = R.drawable.og1fancy;
    private final static int MAP_OG2 = R.drawable.og2fancy;
    private final static int MAP_OG3 = R.drawable.og345;

    public DrawingAssistant(TouchImageView dotView, TouchImageView mapView) {
        DrawingAssistant.mapView = mapView;
        this.dotView = dotView;
        currentPosition = new Node("PointZero",62,44,1);
    }

    public static void setCurrentPosition(Node currentPosition1) {
        currentPosition = currentPosition1;
        if(!pathDrawn) {
            if(currentPosition.getZ() == 0) {
                mapView.setImageResource(MAP_EG);
                currentMap = MAP_EG;
            } else if(currentPosition.getZ() == 1) {
                mapView.setImageResource(MAP_OG1);
                currentMap = MAP_OG1;
            } else if(currentPosition.getZ() == 2) {
                mapView.setImageResource(MAP_OG2);
                currentMap = MAP_OG2;
            } else if(currentPosition.getZ() == 3) {
                mapView.setImageResource(MAP_OG3);
                currentMap = MAP_OG3;
            }
        }
        /*else {
            if(currentPosition.getZ() == 0) {
                mapView.setImageResource(R.drawable.eg);
                currentMap = R.drawable.eg;
            } else if(currentPosition.getZ() == 1) {
                mapView.setImageResource(R.drawable.og1example);
                currentMap = R.drawable.og1example;
            } else if(currentPosition.getZ() == 2) {
                mapView.setImageResource(R.drawable.og2);
                currentMap = R.drawable.og2;
            } else if(currentPosition.getZ() == 3) {
                mapView.setImageResource(R.drawable.og345);
                currentMap = R.drawable.og345;
            }
        }*/
        if(mapConverter != null) {
            //mapConverter.setMapView(MainFragment.getMapView());
        }
    }

    public static void setPathToDestination(List<Node> pathToDestination) {
        System.out.println("DrawingAssistant received Path reaching from Point: "+ pathToDestination.get(0) + " to: " + pathToDestination.get((pathToDestination.size()-1)));
        path =  new ArrayList<>(pathToDestination);
    }

    // https://developer.android.com/training/animation/reposition-view

    public void drawPath() {
        pathDrawn = true;

        Paint paintEG = new Paint();
        Paint paintOG1 = new Paint();
        Paint paintOG2 = new Paint();
        Paint paintOG3 = new Paint();
        Paint paintNewFloor = new Paint();

        paintEG.setColor(Color.RED);
        paintEG.setStrokeWidth(15);

        paintNewFloor.setColor(Color.BLUE);
        paintNewFloor.setStrokeWidth(15);

        Bitmap mutableBitmap = allBitmapsOriginal.get(0);
        float oneX = (float)  mutableBitmap.getWidth()/124f;
        float oneY = (float)  mutableBitmap.getHeight()/88f;

        bitmapsWithPath = new LinkedList<>();

        List<MapPosition> mapPositions = new LinkedList<>();
        for(int i = 0; i< path.size(); i++) {
            Node n = path.get(i);
            MapPosition mapPosition = new MapPosition();
            mapPosition.setX(n.getX()*oneX);
            mapPosition.setY(n.getY()*oneY);
            mapPosition.setZ(n.getZ());
            mapPositions.add(mapPosition);
        }

        bitmapsWithPath.add(allBitmapsOriginal.get(0).copy(Bitmap.Config.ARGB_8888, true));
        bitmapsWithPath.add(allBitmapsOriginal.get(1).copy(Bitmap.Config.ARGB_8888, true));
        bitmapsWithPath.add(allBitmapsOriginal.get(2).copy(Bitmap.Config.ARGB_8888, true));
        bitmapsWithPath.add(allBitmapsOriginal.get(3).copy(Bitmap.Config.ARGB_8888, true));


        for(int i=0;i<(mapPositions.size()-1); i++) {
            mutableBitmap = bitmapsWithPath.get(mapPositions.get(i).getZ());
            Canvas canvas = new Canvas(mutableBitmap);
            if(mapPositions.get(i+1).getY() != mapPositions.get(i).getZ()) {
                canvas.drawLine(mapPositions.get(i).getX(),mapPositions.get(i).getY(),mapPositions.get(i+1).getX(),mapPositions.get(i+1).getY(),paintNewFloor);
            } else {
                canvas.drawLine(mapPositions.get(i).getX(),mapPositions.get(i).getY(),mapPositions.get(i+1).getX(),mapPositions.get(i+1).getY(),paintEG);
            }
        }
        mutableBitmap = bitmapsWithPath.get(currentPosition.getZ());
        mapView.setImageBitmap(mutableBitmap);
        //mapConverter.setMapView(MainFragment.getMapView());
    }

    public void removePath() {
        pathDrawn = false;
        path = new LinkedList<>();
        setCurrentPosition(currentPosition);
    }

    @Override
    public void run() {
        float newX = (float) 0;
        float newY = (float) 0;
        Mover dotMover= new Mover("DotMover",newX,newY);

        dotMover.setView(dotView);
        dotMover.start();

        while(!setHW) {
            if(mapView.getHeight() != 0.0) {
                displayHeight = MainActivity.height;
                displayWidth = MainActivity.width;

                mapHeight = mapView.getHeight();
                mapWidth = mapView.getWidth();

                dotHeight = dotView.getHeight();
                dotWidth = dotView.getWidth();

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
                //mapConverter.setMapView(MainFragment.getMapView());

                Bitmap egBitmap = BitmapFactory.decodeResource(MainActivity.mainContext().getResources(), MAP_EG);
                Bitmap mutableBitmapEG = egBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap og1Bitmap = BitmapFactory.decodeResource(MainActivity.mainContext().getResources(), MAP_OG1);
                Bitmap mutableBitmapOG1 = og1Bitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap og2Bitmap = BitmapFactory.decodeResource(MainActivity.mainContext().getResources(), MAP_OG2);
                Bitmap mutableBitmapOG2 = og2Bitmap.copy(Bitmap.Config.ARGB_8888, true);
                Bitmap og345Bitmap = BitmapFactory.decodeResource(MainActivity.mainContext().getResources(), MAP_OG3);
                Bitmap mutableBitmapOG3 = og345Bitmap.copy(Bitmap.Config.ARGB_8888, true);
                allBitmapsOriginal.add(mutableBitmapEG);
                allBitmapsOriginal.add(mutableBitmapOG1);
                allBitmapsOriginal.add(mutableBitmapOG2);
                allBitmapsOriginal.add(mutableBitmapOG3);

                setHW = true;
            }
        }

        while(true) {
            //System.out.println("----------------------------------------------------------------------");
            dotView.setZoom((float) (2-mapView.getCurrentZoom()));

            if(!path.isEmpty() && !pathDrawn) {
                mapView.setZoom(1.0f);
                drawPath();
            }
            //System.out.println(mapView.getScrollPosition().x);
            //mapConverter.setMapView(MainFragment.getMapView());
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
