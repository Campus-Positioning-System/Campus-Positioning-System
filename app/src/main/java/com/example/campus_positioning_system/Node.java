package com.example.campus_positioning_system;

import java.util.List;
import java.util.LinkedList;

public class Node {
    private String identifier;
    private Integer x, y, z;
    private List<Node> connections;
    public void addConnection(Node other){
        connections.add(other);
    }
    public void removeConnection(Node other){
        if(connections.contains(other)){}
        connections.remove(other);
    }
    public Node(){}
    public Node(String id, int x, int y, int z) {
        this.identifier = id;
        this.x = x;
        this.y = y;
        this.z = z;
        connections = new LinkedList<Node>();
    }
    public Node(Node other){
        this.identifier = other.identifier;
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.connections = other.connections;
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

    public List<Node> getConnections(){
        return connections;
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
