package com.example.campus_positioning_system.RoomList;

import com.example.campus_positioning_system.Node;

import java.io.Serializable;


/** A simple container class for a rooms attributes
 * used for easy serialization and saving of favorites
 * saves:
 * @see com.amrdeveloper.treeview.TreeNode
 * @version 1.0
 * @author Ben Lutz
 */
public class RoomItem implements Serializable {
    /**
     * Name of the room Example: C2.04
     */
    public String name;
    /**
     * Alias of the Room. Example: Aula
     */
    public String alias;
    /**
     * Position of the Graph Node that is closest to the Rooms door
     */
    public String closestNode;

    /**
     * Creates a RoomItem instance
     * @param name Name of the Room
     * @param alias Alias of the Room
     * @param closestNode Closest Node to the Room as X/Y/Z String
     */
    public RoomItem(String name, String alias, String closestNode){
        this.name = name;
        this.alias = alias;
        this.closestNode = closestNode;
    }

    /**
     * Converters a RoomItem to a Node
     * @return returns the RoomItem as Node
     */
    public Node asNode(){
        String [] coords = closestNode.split("/");
        return new Node("",Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
    }
}
