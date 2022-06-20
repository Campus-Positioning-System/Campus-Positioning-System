package com.example.campus_positioning_system.Map;

public class MapPosition {
    private float x;
    private float y;

    public MapPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition() {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
