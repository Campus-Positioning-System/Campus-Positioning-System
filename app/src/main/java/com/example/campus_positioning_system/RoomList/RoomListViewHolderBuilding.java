package com.example.campus_positioning_system.RoomList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

/** Represents a building entry in the Room List
 * used in {@link com.example.campus_positioning_system.Activitys.RoomSelectionActivity}
 * Data is rooted in roomNameList.xml asset
 * @version 1.0
 * @author Ben Lutz
 */

public class RoomListViewHolderBuilding extends TreeViewHolder {

    /**
     * Text and ImageViews displaying the name and a building icon
     * Alias is currently not used but could be used in the future to display the
     * corresponding faculty to a building
     */
    private TextView alias_name, buildingName;
    /**
     * Icon displayed at the beginning of the row
     */
    private ImageView icon;

    /** Constructor called on list creation. Selects displayed data elements
     * @param itemView the View the entry is displayed in
     */
    public RoomListViewHolderBuilding(@NonNull View itemView) {
        super(itemView);
        alias_name = itemView.findViewById(R.id.room_list_building_alias);
        buildingName = itemView.findViewById(R.id.room_list_building_name);
        icon = itemView.findViewById(R.id.room_list_building_icon);

    }

    /** Processes the Building node
     * Building Nodes are currently filled with their name as String
     * @param node Building data
     */
    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        //System.out.println("Processing Building " + node.getValue().toString());
        icon.setImageResource(R.drawable.ic_baseline_house_24);
        buildingName.setText(node.getValue().toString());

    }

}
