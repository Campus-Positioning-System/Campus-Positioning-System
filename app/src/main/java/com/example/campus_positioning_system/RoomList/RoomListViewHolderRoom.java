package com.example.campus_positioning_system.RoomList;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Activitys.RoomSelectionActivity;
import com.example.campus_positioning_system.LocationNavigation.PathfindingControl;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;

import java.util.List;


public class RoomListViewHolderRoom extends TreeViewHolder {

    private TextView roomName, alias;
    private ImageView icon, start_button;

    private RoomSelectionActivity roomSelectionActivity;


    public RoomListViewHolderRoom(@NonNull View itemView,@Nullable RoomSelectionActivity roomSelectionActivity) {
        super(itemView);
        roomName = itemView.findViewById(R.id.room_item_name);
        icon = itemView.findViewById(R.id.list_icon);
        start_button = itemView.findViewById(R.id.item_start_icon);
        alias = itemView.findViewById(R.id.room_item_alias);

        this.roomSelectionActivity = roomSelectionActivity;
    }

    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        System.out.println("Binding value with class " + node.getValue().getClass().toString());

        if(((RoomItem) (node.getValue())).alias != null)
            alias.setText(((RoomItem) node.getValue()).alias);

        roomName.setText(((RoomItem)node.getValue()).name);

        if(RoomListConverter.isFavorite(node))
            icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_24));

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() -> { // Lambda Expression
                    String [] coords = ((RoomItem) (node.getValue())).closestNode.split("/");
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
                System.out.println("User wants to start navigating to " + ((RoomItem) (node.getValue())).name);
                if(roomSelectionActivity != null) {
                    roomSelectionActivity.finish();
                }
            }
        });


        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo Add item to Favorites
                System.out.println("User wants to favorite Room " + ((RoomItem) (node.getValue())).name);

                if(RoomListConverter.isFavorite(node)){
                    System.out.println("Removing favorite");
                    RoomListConverter.removeFavorite(node);
                    icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_border_24));
                }else {
                    System.out.println("Adding to favorites");
                    RoomListConverter.addFavorite(node);
                    icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_24));
                }
            }
        });
    }
}