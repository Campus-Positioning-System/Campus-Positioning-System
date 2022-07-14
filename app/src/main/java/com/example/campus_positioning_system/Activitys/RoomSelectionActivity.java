package com.example.campus_positioning_system.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.amrdeveloper.treeview.TreeViewAdapter;
import com.amrdeveloper.treeview.TreeViewHolderFactory;
import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.RoomList.RoomListConverter;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderBuilding;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderLevel;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderRoom;

/**
 * Reads a campus hierarchy file from FS and displays it as a hierarchical list
 * @author Ben Lutz
 * @version 1.0
 */
public class RoomSelectionActivity extends AppCompatActivity {

    /**
     * List the Rooms will be displayd in
     */
    RecyclerView list;


    /**
     * Creates the room list onCreate of the RoomSelectionActivity
     * @param savedInstanceState State to restore if there is one
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_selection_activity);

        list = findViewById(R.id.room_list);
        list.setLayoutManager(new LinearLayoutManager(this));

        // Set different Adapters for different kinds of list items
        TreeViewHolderFactory factory = (v, layout) -> {
            if (layout == R.layout.room_list_building_item)
                return new RoomListViewHolderBuilding(v);
            else if (layout == R.layout.room_list_level_item)
                return new RoomListViewHolderLevel(v);
            else
                return new RoomListViewHolderRoom(v,this);
        };
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(factory);
        list.setAdapter(treeViewAdapter);

        treeViewAdapter.updateTreeNodes(RoomListConverter.generateTreeNodeList(this));

        RoomListConverter.generateTreeNodeList(this);
    }

    /**
     * Handles going back to the map when the user presses the back button
     */
    @Override
    public void onBackPressed() {
        //System.out.println("User wants to go back from Room list");
        //System.out.println("Navigating from Room List back to Main");
        finish();
    }


}
