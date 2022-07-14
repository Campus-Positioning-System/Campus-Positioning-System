package com.example.campus_positioning_system.RoomList;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.Activitys.FavoritesActivity;
import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.Activitys.RoomSelectionActivity;
import com.example.campus_positioning_system.LocationNavigation.PathfindingControl;
import com.example.campus_positioning_system.Map.DrawingAssistant;
import com.example.campus_positioning_system.Node;
import com.example.campus_positioning_system.R;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;

import java.util.List;

/** Represents a room entry in the Room List
 * used in {@link com.example.campus_positioning_system.Activitys.FavoritesActivity} and {@link com.example.campus_positioning_system.Activitys.RoomSelectionActivity}
 * Data is rooted in roomNameList.xml asset
 * @version 1.0
 * @author Ben Lutz
 */
public class RoomListViewHolderRoom extends TreeViewHolder {

    private TextView roomName, alias;
    private ImageView icon, start_button;

    /**
     * Activity the list was generated in. Used to navigate back to map after route was started
     */
    private Activity roomSelectionActivity;

    /**
     * Constructor called on list creation. Selects displayed data elements and fills Activity
     * @param itemView View the displayed entry will be in
     * @param roomSelectionActivity Activity the list was generated. Also see {@link com.example.campus_positioning_system.RoomList.RoomListViewHolderRoom#roomSelectionActivity}
     */
    public RoomListViewHolderRoom(@NonNull View itemView,@Nullable Activity roomSelectionActivity) {
        super(itemView);
        //If the Room list was generated in the FavoritesActivity, show items not indented
        if(roomSelectionActivity.getClass() == FavoritesActivity.class)
            ((LinearLayout)itemView.findViewById(R.id.room_list_linear_layout)).setGravity(Gravity.CENTER);
        roomName = itemView.findViewById(R.id.room_item_name);
        icon = itemView.findViewById(R.id.list_icon);
        start_button = itemView.findViewById(R.id.item_start_icon);
        alias = itemView.findViewById(R.id.room_item_alias);
        this.roomSelectionActivity = roomSelectionActivity;
    }



    /** Processes the Room node
     * Room nodes are currently filled with {@link com.example.campus_positioning_system.RoomList.RoomItem} instances
     * @param node Room data containing {@link com.example.campus_positioning_system.RoomList.RoomItem} and a layout ID
     */
    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);

        // Set alias if there is one
        if(((RoomItem) (node.getValue())).alias != null && !((RoomItem) (node.getValue())).alias.isEmpty())
            alias.setText(((RoomItem) node.getValue()).alias);
        else
            alias.setVisibility(View.INVISIBLE);



        // Set room name
        roomName.setText(((RoomItem)node.getValue()).name);

        // Set favorite icon if the room is a favorite
        if(RoomListConverter.isFavorite(node))
            icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_24));

        // OnClick listener for the start button
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Handle the pathfinding and drawing on a new thread for responsiveness
                new Thread(() -> {
                    // Update the target location to the closest node of the selected room
                    PathfindingControl.updateTargetLocation(((RoomItem) (node.getValue())).asNode());
                    // Set the navigation path to the calculated path
                    DrawingAssistant.setPathToDestination(PathfindingControl.calculatePath());
                }).start();

                // Navigate back to map by ending the roomSelectionActivity
                if(roomSelectionActivity != null) {
                    roomSelectionActivity.finish();
                }
            }
        });

        // OnClickListener for the favorite icon
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RoomListConverter.isFavorite(node)){ // User wants to remove the room as favorite
                    // Remove Favorite
                    RoomListConverter.removeFavorite(node);
                    // and update icon
                    icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_border_24));
                }else { // User wants to favorite a room
                    // Add favorite
                    RoomListConverter.addFavorite(node);
                    // and update Icon
                    icon.setImageDrawable(AppCompatResources.getDrawable(MainActivity.mainContext(),R.drawable.ic_baseline_favorite_24));
                }
            }
        });
    }
}