package com.example.campus_positioning_system;

public class Distance{
    public double distance(final NNObject a, final NNObject b) {
        return Math.abs(a.getRssi()-b.getRssi());
    }
}
