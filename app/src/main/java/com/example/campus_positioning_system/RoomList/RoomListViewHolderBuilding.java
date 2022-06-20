package com.example.campus_positioning_system.RoomList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

public class RoomListViewHolderBuilding extends TreeViewHolder {

    private TextView alias_name, buildingName;
    private ImageView icon, start_button;

    public RoomListViewHolderBuilding(@NonNull View itemView) {
        super(itemView);
        alias_name = itemView.findViewById(R.id.room_list_building_value);
        buildingName = itemView.findViewById(R.id.room_list_building_item_desc);
        icon = itemView.findViewById(R.id.room_list_building_icon);

    }

    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        System.out.println("Processing Building " + node.getValue().toString());
        icon.setImageResource(R.drawable.ic_baseline_house_24);
        buildingName.setText(node.getValue().toString());

    }

}
