package com.example.campus_positioning_system.RoomList;

import android.app.Instrumentation;
import android.content.Context;

import androidx.room.Room;

import com.amrdeveloper.treeview.TreeNode;
import com.example.campus_positioning_system.R;

import org.apache.commons.lang3.SerializationUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.google.gson.Gson;

public class RoomListConverter {

    static List<RoomItem> favorites;
    static Context myContext;

    public static List<RoomItem> getFavorites() {

        if (favorites != null)
            return favorites;

        readFavorites();

        return favorites;
    }

    public static void setMyContext(Context c){
        if(myContext == null)
            myContext = c;
    }

    public static List<TreeNode> getFavoritesAsNodes() {


        List<TreeNode> ret = new ArrayList<>();
        readFavorites();
        if(favorites == null)
            return ret;



        for (RoomItem r : favorites)
            ret.add(new TreeNode(r,R.layout.room_list_room_item));

        return ret;
    }

    static synchronized void saveFavorites() {

        try (ObjectOutputStream oos = new ObjectOutputStream(myContext.openFileOutput("favorites.data", Context.MODE_PRIVATE))) {
            oos.writeObject(favorites);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void readFavorites(){

        try (ObjectInputStream oos = new ObjectInputStream(myContext.openFileInput("favorites.data"))) {
            favorites = (List<RoomItem>) oos.readObject();
            if(favorites == null)
                favorites = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(favorites == null)
                favorites = new ArrayList<>();
        }

    }


    public static void addFavorite(TreeNode nodeItem) {
        if(favorites == null)
            getFavorites();
        favorites.add((RoomItem) nodeItem.getValue());
        saveFavorites();
    }

    public static void removeFavorite(TreeNode nodeItem) {
        if(favorites == null)
            getFavorites();
        favorites.remove((RoomItem)nodeItem.getValue());
        saveFavorites();
    }

    public static boolean isFavorite(TreeNode node){
        if (favorites == null)
            return false;
        return favorites.contains((RoomItem) node.getValue());
    }

    public static List<TreeNode> printList(Context c) {
        myContext = c;
        List<TreeNode> roots = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document listDoc = dBuilder.parse(c.getAssets().open("roomNameList.xml"));


            System.out.println("Root element :" + listDoc.getDocumentElement().getNodeName());
            NodeList nList = listDoc.getElementsByTagName("building");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                if (nList.item(temp).getNodeType() == Node.ELEMENT_NODE) {
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
                                    RoomItem roomAttributes = new RoomItem(
                                            ((Element) floors.item(j)).getAttribute("roomname"),
                                            floors.item(j).getChildNodes().item(1).getTextContent(),
                                            floors.item(j).getChildNodes().item(3).getTextContent()
                                    );
                                    TreeNode roomNode = new TreeNode(roomAttributes, R.layout.room_list_room_item);
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
