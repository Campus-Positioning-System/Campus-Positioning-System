package com.example.campus_positioning_system;

import java.util.List;
import java.util.LinkedList;

public class Node implements Comparable<Node>{
    private String identifier;
    private Integer x, y, z;

    public Node(){}
    public Node(String id, int x, int y, int z) {
        this.identifier = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Node(Node other){
        this.identifier = other.identifier;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }


    public String toString(){
        return x + "/" + y  + "/" + z + "/" + identifier;       // x/y/z/identifier
    }
    public boolean equals(Node other){
        return this.x == other.x && this.y == other.y && this.z == other.z && this.identifier.equals(other.identifier);
    }

    public int compareTo(Node other){
        if(this.z.equals(other.z)) return this.x.equals(other.x) ? this.y.compareTo(other.y) : this.x.compareTo(other.x);
        return this.z.compareTo(other.z);
    }
}
