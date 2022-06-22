package com.example.campus_positioning_system.Map;

public class MapPosition {
    private float x;
    private float y;
    private int z;


    public MapPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MapPosition(float x, float y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MapPosition() {

    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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
