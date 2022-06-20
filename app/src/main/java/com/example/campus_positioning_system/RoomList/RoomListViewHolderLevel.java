package com.example.campus_positioning_system.RoomList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

public class RoomListViewHolderLevel extends TreeViewHolder {

    private TextView alias, levelName;
    private ImageView icon, start_button;

    public RoomListViewHolderLevel(@NonNull View itemView) {
        super(itemView);
        alias = itemView.findViewById(R.id.room_list_level_value);
        levelName = itemView.findViewById(R.id.room_list_level_item_desc);
        icon = itemView.findViewById(R.id.room_list_level_icon);

    }

    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        System.out.println("Processing Level " + node.getValue().toString());
        icon.setImageResource(R.drawable.ic_baseline_stairs_24);
        levelName.setText(node.getValue().toString());

    }

}
