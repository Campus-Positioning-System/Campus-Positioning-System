package com.example.campus_positioning_system;

import com.example.campus_positioning_system.Node;

public class CNode implements Comparable<CNode> {
    private Integer count;
    private Node parent;

    public CNode(Node parent, int count){
        this.parent = parent;
        this.count = count;
    }

    public Node getParent(){
        return parent;
    }
    public int increment(){
        return count++;
    }
    public int compareTo(CNode other){
        return this.parent.compareTo(other.parent);
    }
    public Integer getCount(){
        return count;
    }
    
}
