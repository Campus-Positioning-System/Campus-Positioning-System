package com.example.campus_positioning_system.Map;

/* Wrapper Class to transform a Node to a Position on the Display
*
* Input:
* - Node
*
* Output:
* - Display position in x and y coordinates
*   for example x: 10px y: 100px etc.
*
 */

import android.graphics.PointF;

import com.example.campus_positioning_system.Node;
import com.ortiz.touchview.TouchImageView;

public class MapConverter {

    //x y z from our Coordinate System that we put on the map
    private final int x = 124;
    private final int y = 88;
    private final int z = 5;

    private final TouchImageView mapView;

    private float currentZoom;

    private final float pngHeight = 994;
    private final float pngWidth = 706;

    private final float xCoordinatesGap;
    private final float yCoordinatesGap;

    private final float yShiftMap;

    private final float mapWidth;
    private final float mapHeight;

    private final float dotWidth;
    private final float dotHeight;


    public MapConverter(float mapHeight, float mapWidth, float dotHeight, float dotWidth, TouchImageView mapView) {
        this.mapView = mapView;
        this.xCoordinatesGap = (float)1/x;
        this.yCoordinatesGap = ((((float)mapWidth/994) * 706)/mapHeight)/y;
        this.yShiftMap = (float)0.5 - ((((float)mapWidth/994) * 706)/mapHeight)/2;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.dotHeight = dotHeight;
        this.dotWidth = dotWidth;
    }

    public MapPosition convertNode(Node toConvert, float canvasSizeX, float canvasSizeY) {
        MapPosition mapPos = new MapPosition();
        //The getScrollPosition is dependant on the View.
        //So x:0 y:0 would be the left top corner of the VIEW
        PointF mapViewCenter = mapView.getScrollPosition();

        //Calculate Center:
        float centerX = getXCenter(mapViewCenter.x);
        float centerY = getYCenter(mapViewCenter.y);
      //  System.out.println("Center of mapView(x,y) without Shift: " + mapViewCenter.x + " " + mapViewCenter.y);
      //  System.out.println("Center of mapView(x,y): " + centerX + " " + centerY);

        float xShift;
        float yShift;

        currentZoom = mapView.getCurrentZoom();

        xShift = -(mapViewCenter.x - (float) 0.5)*currentZoom;
        yShift = -(mapViewCenter.y - (float) 0.5)*(float)(1+(1/currentZoom)-0.1);

        float xForNode = getXCenter((toConvert.getX()*xCoordinatesGap))+xShift;
        float yForNode = getYCenter((toConvert.getY()*yCoordinatesGap)+ yShiftMap)+yShift;
       // System.out.println(xForNode);
       // System.out.println(yForNode);
        float posX = xForNode * (canvasSizeX) - (dotWidth/(float)2);
        float posY = yForNode * (mapWidth) - (dotHeight/(float)2);

        mapPos.setX(posX);
        mapPos.setY(posY);
        return mapPos;
    }

    public MapPosition convertNode(Node toConvert) {
        MapPosition mapPos = new MapPosition();
        //The getScrollPosition is dependant on the View.
        //So x:0 y:0 would be the left top corner of the VIEW
        PointF mapViewCenter = mapView.getScrollPosition();

        //Calculate Center:
        float centerX = getXCenter(mapViewCenter.x);
        float centerY = getYCenter(mapViewCenter.y);
        //  System.out.println("Center of mapView(x,y) without Shift: " + mapViewCenter.x + " " + mapViewCenter.y);
        //  System.out.println("Center of mapView(x,y): " + centerX + " " + centerY);

        float xShift;
        float yShift;

        currentZoom = mapView.getCurrentZoom();

        xShift = -(mapViewCenter.x - (float) 0.5)*currentZoom;
        yShift = -(mapViewCenter.y - (float) 0.5)*(float)(1+(1/currentZoom)-0.1);

        float xForNode = getXCenter((toConvert.getX()*xCoordinatesGap))+xShift;
        float yForNode = getYCenter((toConvert.getY()*yCoordinatesGap)+ yShiftMap)+yShift;
        // System.out.println(xForNode);
        // System.out.println(yForNode);
        mapPos.setX(xForNode * (mapWidth) - (dotWidth/(float)2));
        mapPos.setY(yForNode * (mapHeight) - (dotHeight/(float)2));
        return mapPos;
    }

    /*
    Converts a relative float Position of the Screen and multiplies it with the zoom
     */
    public float getYCenter(float toConvert) {
        float result;
        result = ((toConvert - (float)0.5) * currentZoom) + (float)0.5;
        return result;
    }

    /*
    Converts a relative float Position of the Screen and multiplies it with the zoom
     */
    public float getXCenter(float toConvert) {
        float result;
        result = ((toConvert - (float)0.5) * currentZoom) + (float)0.5;
        return result;
    }

    public float getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(float currentZoom) {
        this.currentZoom = currentZoom;
    }
}
