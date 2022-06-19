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

import android.view.View;

import com.example.campus_positioning_system.Node;
import com.ortiz.touchview.TouchImageView;

public class MapConverter {
    private final float mapHeight;
    private final float mapWidth;

    private TouchImageView mapView;

    private float currentZoom;

    public MapConverter(float mapHeight, float mapWidth, TouchImageView mapView) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.mapView = mapView;
    }

    public MapPosition convertNode(Node toConvert) {
        return null;
    }

    public float getRealCenter(float toConvert, float factor) {
        float result;
        currentZoom = mapView.getCurrentZoom();
        if(0 > 0.5 - toConvert) {
            result = (float) (0.5 - (Math.abs(0.5 - toConvert)*(currentZoom-factor)));
        } else {
            result = (float) (0.5 + (Math.abs(0.5 - toConvert)*(currentZoom-factor)));
        }
        return result;
    }

    public float getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(float currentZoom) {
        this.currentZoom = currentZoom;
    }
}
