package com.example.campus_positioning_system.Database;

import androidx.room.TypeConverter;

import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.Node;

public class Converters {
    @TypeConverter
    public static Node fromString(String value) {       // x/y/z/identifier
        String[] nodeAsArray = value.split("[!/]+");
        if(nodeAsArray.length < 4){ // FixMe Z Coord is floored bcz its supposed to be string but in csv is sometimes 0.5
            return new Node("", Integer.parseInt(nodeAsArray[0]), Integer.parseInt(nodeAsArray[1]), (int) Math.floor(Float.parseFloat(nodeAsArray[2])));
        }
        return new Node(nodeAsArray[3], Integer.parseInt(nodeAsArray[0]), Integer.parseInt(nodeAsArray[1]), Integer.parseInt(nodeAsArray[2]));
    }
    @TypeConverter
    public static String fromNode(Node node) {
        return node.toString();
    }

    /**
     * Converts a CSV Line to NNObject
     * @param line One line from the CSV, is not checked for validity
     * @return NNObject with data from line
     */
     @TypeConverter
    public static NNObject nnObjectFromString(String line){
        /*
        Index:         0    1    2    3    4    5
        Example:    PUNKT;GRAD;SSID;BSS;RSSI;KANAL
                    55/60/0;0;HFU Guest;18:E8:29:EE:30:6E;-83;36
         */

         String[] LineAsStringArray = line.split("[!;]+");
         // NNObject Constructor: String mac, Float rssi, Node location, Integer angle
         return new NNObject(LineAsStringArray[3], Float.parseFloat(LineAsStringArray[4]), fromString(LineAsStringArray[0]),Integer.parseInt(LineAsStringArray[1]));

     }
}
