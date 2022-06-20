package com.example.campus_positioning_system.RoomList;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

import org.w3c.dom.Element;


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
                System.out.println("User wants to start navigating to " + ((Element) (node.getValue())).getElementsByTagName("roomclosestnode").item(0).getTextContent());
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo Add item to Favorites
                System.out.println("User wants to favorite Room " + ((Element) (node.getValue())).getAttribute("roomname"));
            }
        });

    }
}