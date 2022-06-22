package com.example.campus_positioning_system.RoomList;

import android.content.Context;

import com.amrdeveloper.treeview.TreeNode;
import com.example.campus_positioning_system.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RoomListConverter {

    public static void addFavorite(TreeNode nodeItem){

    }

    public static List<TreeNode> printList(Context c){
        List<TreeNode> roots = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(c.getAssets().open("roomNameList.xml"));
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("building");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                if(nList.item(temp).getNodeType() == Node.ELEMENT_NODE) {
                    NodeList building = nList.item(temp).getChildNodes(); // Floors
                    System.out.println("We are in building " + ((Element) nList.item(temp)).getAttribute("buildingname"));
                    TreeNode buildingNode = new TreeNode(((Element) nList.item(temp)).getAttribute("buildingname"), R.layout.room_list_building_item);

                    for (int i = 0; i < building.getLength(); i++) {
                        if (building.item(i).getNodeType() == Node.ELEMENT_NODE) {
                            NodeList floors = building.item(i).getChildNodes();
                            System.out.println("\tWe are in floor " + ((Element) building.item(i)).getAttribute("floorname"));
                            TreeNode floorNode = new TreeNode(((Element) building.item(i)).getAttribute("floorname"), R.layout.room_list_level_item);

                            for (int j = 0; j < floors.getLength(); j++) {
                                if (floors.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                    System.out.println("\t\tWe are in room " + ((Element) floors.item(j)).getAttribute("roomname"));
                                    TreeNode roomNode = new TreeNode(((Element) floors.item(j)),R.layout.room_list_room_item);

                                    NodeList roomAttributes = floors.item(j).getChildNodes();
                                    for (int k = 0; k < roomAttributes.getLength(); k++) {
                                        if (roomAttributes.item(k).getNodeType() == Node.ELEMENT_NODE){
                                            System.out.println("\t\t\tAttribute : " + roomAttributes.item(k).getNodeName() + " has value " + roomAttributes.item(k).getTextContent());
                                        }
                                    }
                                    floorNode.addChild(roomNode);
                                }
                            }
                            buildingNode.addChild(floorNode);
                        }
                    }
                    roots.add(buildingNode);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roots;
    }
}
