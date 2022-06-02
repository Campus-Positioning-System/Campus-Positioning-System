package com.example.campus_positioning_system;

public class DrawingAssistant {
    private Node currentPosition;
    private Node destination;

    //x y z from our Coordinate System that we put on the map
    private int x = 124;
    private int y = 88;
    private int z = 5;

    DrawingAssistant() {
    }

    public void setCurrentPosition(Node currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }



}
