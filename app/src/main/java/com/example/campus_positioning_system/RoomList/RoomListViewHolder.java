package com.example.campus_positioning_system.RoomList;


import android.icu.util.ValueIterator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewHolder;
import com.example.campus_positioning_system.R;

import org.w3c.dom.Element;

import java.io.File;




public class RoomListViewHolder extends TreeViewHolder {

    private TextView text;

    //ToDo Fill other fields with text
    //ToDo add eventListener for navigation start
    //ToDo Styling

    public RoomListViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.room_item_desc);
    }

    @Override
    public void bindTreeNode(TreeNode node) {
        super.bindTreeNode(node);
        System.out.println("Binding value with class " + node.getValue().getClass().toString());
        if (node.getValue() instanceof Element){
            text.setText(((Element) (node.getValue())).getAttribute("roomname"));
        }else {
            text.setText(node.getValue().toString());
        }

    }
}