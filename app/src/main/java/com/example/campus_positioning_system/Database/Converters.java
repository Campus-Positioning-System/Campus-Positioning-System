package com.example.campus_positioning_system.Database;

import androidx.room.TypeConverter;

import com.example.campus_positioning_system.Node;

public class Converters {
    @TypeConverter
    public static Node fromString(String value) {       // x/y/z/identifier
        String[] nodeAsArray = value.split("[!/]+");
        if(nodeAsArray.length < 4){
            return new Node("", Integer.parseInt(nodeAsArray[0]), Integer.parseInt(nodeAsArray[1]), Integer.parseInt(nodeAsArray[2]));
        }
        return new Node(nodeAsArray[3], Integer.parseInt(nodeAsArray[0]), Integer.parseInt(nodeAsArray[1]), Integer.parseInt(nodeAsArray[2]));
    }
    @TypeConverter
    public static String fromNode(Node node) {
        return node.toString();
    }
}
