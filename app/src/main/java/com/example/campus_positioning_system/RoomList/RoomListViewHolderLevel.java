package com.example.campus_positioning_system.RoomList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

/** Represents a room entry in the Room List
 * used in {@link com.example.campus_positioning_system.Activitys.RoomSelectionActivity}
 * Data is rooted in roomNameList.xml asset
 * @version 1.0
 * @author Ben Lutz
 */
public class RoomListViewHolderLevel extends TreeViewHolder {

    /**
     * TextView where the name and alias of the floor will be displayed
     * alias is currently not used
     */
    private TextView alias, levelName;
    /**
     * Icon displayed at the beginning of the row
     */
    private ImageView icon;

    /**
     * Constructor called on list creation. Selects the data elements later used to fill with content
     * @param itemView View the displayed entry will be in
     */
    public RoomListViewHolderLevel(@NonNull View itemView) {
        super(itemView);
        alias = itemView.findViewById(R.id.room_list_level_value);
        levelName = itemView.findViewById(R.id.room_list_level_item_desc);
        icon = itemView.findViewById(R.id.room_list_level_icon);

    }

    /**
     * Fills data elements with data from node
     * @param node Room data containing all attributes of a floor
     */
    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        //System.out.println("Processing Level " + node.getValue().toString());
        icon.setImageResource(R.drawable.ic_baseline_stairs_24);
        levelName.setText(node.getValue().toString());

    }

}
