package com.example.campus_positioning_system.RoomList;

import java.io.Serializable;

public class RoomItem implements Serializable {
    public String name;
    public String alias;
    public String closestNode;
    public RoomItem(String name, String alias, String closestNode){
        this.name = name;
        this.alias = alias;
        this.closestNode = closestNode;
    }
}
