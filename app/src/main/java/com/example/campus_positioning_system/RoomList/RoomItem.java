package com.example.campus_positioning_system.RoomList;

import com.example.campus_positioning_system.Node;

import java.io.Serializable;


/** A simple container class for a rooms attributes
 * used for easy serialization and saving of favorites
 * @version 1.0
 * @author Ben Lutz
 */
public class RoomItem implements Serializable {
    public String name;
    public String alias;
    public String closestNode;
    public RoomItem(String name, String alias, String closestNode){
        this.name = name;
        this.alias = alias;
        this.closestNode = closestNode;
    }

    public Node asNode(){
        String [] coords = closestNode.split("/");
        return new Node("",Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
    }
}
