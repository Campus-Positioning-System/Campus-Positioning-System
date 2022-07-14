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


/**
 * Class with a number of utility functions for the handling of Room and Favorites List
 * handles loading the Room List from XML, saving and reading favorites,
 * adding and removing favorites
 * Related classes:
 * @see TreeNode
 * @see RoomItem
 * @version 1.0
 * @author Ben Lutz
 */
public class RoomListConverter {

    /**
     * Hold the favorites at runtime
     */
    static List<RoomItem> favorites;
    /**
     * Context of the class, needed for saving and reading favorites from FS
     */
    static Context myContext;

    /**
     * Getter function for the favorites the user currently has
     * @see RoomItem
     * @return Returns a list of RoomItems
     */
    public static List<RoomItem> getFavorites() {

        if (favorites != null)
            return favorites; // Return favorites if they were already read

        // Read the favorites if there are none
        readFavorites();

        return favorites;
    }

    /**
     * Setter for the current context, needed for saving and reading favorites from FS
     * @param c Context which will be set
     */
    public static void setMyContext(Context c){
        if(myContext == null)
            myContext = c;
    }

    /**
     * Getter for the Favorites as TreeNodes
     * @return Returns the favorites as a List of TreeNodes
     */
    public static List<TreeNode> getFavoritesAsNodes() {


        List<TreeNode> ret = new ArrayList<>();
        readFavorites(); // Read favorites
        if(favorites == null) // If there are no favorites, return an empty list
            return ret;


        // If there are favorites
        for (RoomItem r : favorites) //Convert them back to TreeNodes
            ret.add(new TreeNode(r,R.layout.room_list_room_item));

        return ret;
    }

    /**
     * Saves the favorites to a FS
     */
    static synchronized void saveFavorites() {

        try (ObjectOutputStream oos = new ObjectOutputStream(myContext.openFileOutput("favorites.data", Context.MODE_PRIVATE))) {
            oos.writeObject(favorites);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads the favorites from the FS
     */
    static void readFavorites(){

        try (ObjectInputStream oos = new ObjectInputStream(myContext.openFileInput("favorites.data"))) {
            favorites = (List<RoomItem>) oos.readObject();
            if(favorites == null) // If the Favorites List File was empty...
                favorites = new ArrayList<>(); // Initialize it empty
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(favorites == null) // If the Favorites List wasn't written...
                favorites = new ArrayList<>();  // Initialize it empty
        }

    }

    /**
     * Adds a Room to the favorites List
     * @param nodeItem Item to add to favorites
     */
    public static void addFavorite(TreeNode nodeItem) {
        if(favorites == null)
            getFavorites(); // Read favorites if they were not read yet
        favorites.add((RoomItem) nodeItem.getValue()); // Cast and add RoomItem to the list
        saveFavorites(); // save new list of favorites
    }

    /**
     * Remove a Room from the favorites List
     * @param nodeItem Room to remove from favorites
     */
    public static void removeFavorite(TreeNode nodeItem) {
        if(favorites == null)
            getFavorites(); // Read favorites if they were not read yet
        favorites.remove((RoomItem)nodeItem.getValue()); // remove the favorite
        saveFavorites(); // save new list of favorites
    }

    /**
     * Check if a room is a favorite or not
     * @param node Room to check
     * @return Returns true if supplied Room is a favorite, else false
     */
    public static boolean isFavorite(TreeNode node){
        if (favorites == null)
            return false; // If there is no favorites, the node won't be one
        return favorites.contains((RoomItem) node.getValue());
    }

    /**
     * Generates Hierarchical Structure of Buildings, Floors and Rooms from the roomNameList.xml file
     * @param c Context to read roomNameList.xml from
     * @return Returns a List of TreeNodes. The TreeNodes are hierarchical
     */
    public static List<TreeNode> generateTreeNodeList(Context c) {
        myContext = c;
        List<TreeNode> campus = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document listDoc = dBuilder.parse(c.getAssets().open("roomNameList.xml"));


            //System.out.println("Root element :" + listDoc.getDocumentElement().getNodeName());
            NodeList nList = listDoc.getElementsByTagName("building");
            //System.out.println("----------------------------");
            // Read the hierarchical Structure, starting with all Buildings
            for (int buildingCounter = 0; buildingCounter < nList.getLength(); buildingCounter++) {
                if (nList.item(buildingCounter).getNodeType() == Node.ELEMENT_NODE) { // There are other elements that are not relevant,
                    NodeList building = nList.item(buildingCounter).getChildNodes(); // Floors
                    //System.out.println("We are in building " + ((Element) nList.item(buildingCounter)).getAttribute("buildingname"));
                    // Tree nodes get different Values and layouts depending on type, this is important for visualizing later
                    // Buildings get all information from the XML as Element type
                    TreeNode buildingNode = new TreeNode(((Element) nList.item(buildingCounter)).getAttribute("buildingname"), R.layout.room_list_building_item);

                    for (int floorCounter = 0; floorCounter < building.getLength(); floorCounter++) { // Read all Rooms in the building
                        if (building.item(floorCounter).getNodeType() == Node.ELEMENT_NODE) {
                            NodeList floors = building.item(floorCounter).getChildNodes();
                            //System.out.println("\tWe are in floor " + ((Element) building.item(floorCounter)).getAttribute("floorname"));
                            // Floors get all information from the XML as Element type
                            TreeNode floorNode = new TreeNode(((Element) building.item(floorCounter)).getAttribute("floorname"), R.layout.room_list_level_item);

                            for (int roomCounter = 0; roomCounter < floors.getLength(); roomCounter++) { // Read all attributes of a room
                                if (floors.item(roomCounter).getNodeType() == Node.ELEMENT_NODE) {
                                    //System.out.println("\t\tWe are in room " + ((Element) floors.item(roomCounter)).getAttribute("roomname"));
                                    RoomItem roomAttributes = new RoomItem(
                                            ((Element) floors.item(roomCounter)).getAttribute("roomname"),
                                            floors.item(roomCounter).getChildNodes().item(1).getTextContent(),
                                            floors.item(roomCounter).getChildNodes().item(3).getTextContent()
                                    );
                                    // Rooms get their attributes as RoomItem, this is needed for serialisation in save/readFavorites
                                    TreeNode roomNode = new TreeNode(roomAttributes, R.layout.room_list_room_item);
                                    floorNode.addChild(roomNode); // Add room as a child of floor
                                }
                            }
                            buildingNode.addChild(floorNode); // Add floor as a child of Building
                        }
                    }
                    campus.add(buildingNode); // Add Building as a child of campus
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campus;
    }
}
