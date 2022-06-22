package com.example.campus_positioning_system.RoomList;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.LocationNavigation.PathfindingControl;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;

import org.w3c.dom.Element;

import java.util.List;


public class RoomListViewHolderRoom extends TreeViewHolder {

    private TextView roomName, alias;
    private ImageView icon, start_button;

    //ToDo Fill other fields with text
    //ToDo add eventListener for navigation start
    //ToDo Styling

    public RoomListViewHolderRoom(@NonNull View itemView) {
        super(itemView);

        roomName = itemView.findViewById(R.id.room_item_name);
        icon = itemView.findViewById(R.id.list_icon);
        start_button = itemView.findViewById(R.id.item_start_icon);
        alias = itemView.findViewById(R.id.room_item_alias);
    }

    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        System.out.println("Binding value with class " + node.getValue().getClass().toString());

        if(((Element) (node.getValue())).getElementsByTagName("roomalias").item(0) != null)
            alias.setText(((Element) (node.getValue())).getElementsByTagName("roomalias").item(0).getTextContent());

        roomName.setText(((Element) (node.getValue())).getAttribute("roomname"));


        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo Here the navigation can start
                new Thread(() -> { // Lambda Expression
                    String [] coords = ((Element) (node.getValue())).getElementsByTagName("roomclosestnode").item(0).getTextContent().split("/");
                    Node targetNode = new Node("",Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
                    System.out.println("Target node is: " + targetNode);

                    System.out.println("Updating target location of PathfindingControl");
                    PathfindingControl.updateTargetLocation(targetNode);

                    // Set path to destination with Path
                    System.out.println("Calculating Path");

                    List<Node> path = PathfindingControl.calculatePath();
                    System.out.println("Setting Path to Destination in DrawingAssistant");
                    DrawingAssistant.setPathToDestination(path);
                }).start();
                System.out.println("User wants to start navigating to " + ((Element) (node.getValue())).getElementsByTagName("roomclosestnode").item(0).getTextContent());
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo Add item to Favorites
                System.out.println("User wants to favorite Room " + ((Element) (node.getValue())).getAttribute("roomname"));
                RoomListConverter.addFavorite(node);

            }
        });
    }
}