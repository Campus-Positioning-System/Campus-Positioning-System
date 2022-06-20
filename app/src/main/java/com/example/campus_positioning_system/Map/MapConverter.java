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
    private final float mapHeight;
    private final float mapWidth;

    //x y z from our Coordinate System that we put on the map
    private final int x = 124;
    private final int y = 88;
    private final int z = 5;

    private TouchImageView mapView;

    private float currentZoom;

    private final float pngHeight = 994;
    private final float pngWidth = 706;

    private float xCoordinatesGap;
    private float yCoordinatesGap;

    private float yShiftMap;


    public MapConverter(float mapHeight, float mapWidth, TouchImageView mapView) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.mapView = mapView;
        this.xCoordinatesGap = (float)1/x;
        this.yCoordinatesGap = ((((float)mapWidth/994) * 706)/mapHeight)/y;
        this.yShiftMap = (float)0.5 - ((((float)mapWidth/994) * 706)/mapHeight)/2;
    }

    public MapPosition convertNode(Node toConvert) {
        MapPosition mapPos = new MapPosition();
        //The getScrollPosition is dependant on the View.
        //So x:0 y:0 would be the left top corner of the VIEW
        PointF mapViewCenter = mapView.getScrollPosition();

        //Calculate Center:
        float centerX = getXCenter(mapViewCenter.x);
        float centerY = getYCenter(mapViewCenter.y);
        System.out.println("Center of mapView(x,y) without Shift: " + mapViewCenter.x + " " + mapViewCenter.y);
        System.out.println("Center of mapView(x,y): " + centerX + " " + centerY);

        float xShift;
        float yShift;

        currentZoom = mapView.getCurrentZoom();

        xShift = -(mapViewCenter.x - (float) 0.5)*currentZoom;
        yShift = -(mapViewCenter.y - (float) 0.5)*(float)(1+(1/currentZoom)-0.1);

        float xForNode = getXCenter((toConvert.getX()*xCoordinatesGap))+xShift;
        float yForNode = getYCenter((toConvert.getY()*yCoordinatesGap)+ yShiftMap)+yShift;
        System.out.println(xForNode);
        System.out.println(yForNode);
        mapPos.setX(xForNode);
        mapPos.setY(yForNode);
        return mapPos;
    }


    public float getYCenter(float toConvert) {
        float result;
        result = ((toConvert - (float)0.5) * currentZoom) + (float)0.5;
        /*
        if(0.5 > toConvert) {
            result = (float) (0.5 - (Math.abs(0.5 - toConvert)*(1+(1/currentZoom)-0.1)));
        } else {
            result = (float) (0.5 + (Math.abs(0.5 - toConvert)*(1+(1/currentZoom)-0.1)));
        }
         */
        return result;
    }

    public float getXCenter(float toConvert) {
        float result;
        result = ((toConvert - (float)0.5) * currentZoom) + (float)0.5;
        /*
        if(0.5 > toConvert) {
            result = (float) (0.5 - (Math.abs(0.5 - toConvert)*currentZoom));
        } else {
            result = (float) (0.5 + (Math.abs(0.5 - toConvert)*currentZoom));
        }
         */
        return result;
    }

    public float getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(float currentZoom) {
        this.currentZoom = currentZoom;
    }
}
