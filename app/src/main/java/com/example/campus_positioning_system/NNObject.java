package com.example.campus_positioning_system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NNObject implements Comparable<NNObject> {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "Location")
    final private Node location;

    @ColumnInfo(name = "Angle")
    final private Integer angle;

    @ColumnInfo(name = "RSSI")
    final private Float rssi;

    @ColumnInfo(name = "Mac")
    final private String mac;



    public NNObject(String mac, Float rssi, Node location, Integer angle) {
        this.location = location;
        this.angle = angle;
        this.rssi = rssi;
        this.mac = mac;
    }

    protected NNObject(NNObject other) {
        this.location = other.location;
        this.rssi = other.rssi;
        this.mac = other.mac;
        this.angle = other.angle;
    }

    public Integer getAngle() {
        return angle;
    }

    public Float getRssi() {
        return rssi;
    }

    public String getMac() {
        return mac;
    }

    public Node getLocation() {
        return location;
    }

    // Sort lexicographic First Mac-Adress, then Rssi
    public int compareTo(NNObject other) {
        int b = this.getMac().compareTo(other.getMac());
        if (b == 0)
            return this.getAngle().equals(other.getAngle()) ? this.getRssi().compareTo(other.getRssi())
                    : this.getAngle().compareTo(other.getAngle());
        return b;
    }

    public String toString() {
        return "Mac Adresse: " + mac + " RSSI: " + rssi + " Position: " + location;
    }

}