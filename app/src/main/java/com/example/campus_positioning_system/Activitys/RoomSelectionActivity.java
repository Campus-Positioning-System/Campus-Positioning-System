package com.example.campus_positioning_system.Activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.amrdeveloper.treeview.TreeViewAdapter;
import com.amrdeveloper.treeview.TreeViewHolderFactory;
import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.RoomList.RoomListConverter;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderRoom;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderBuilding;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderLevel;


public class RoomSelectionActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_selection_activity);

        list = findViewById(R.id.room_list);
        list.setLayoutManager(new LinearLayoutManager(this));

        TreeViewHolderFactory factory = (v, layout) -> {
            if (layout == R.layout.room_list_building_item)
                return new RoomListViewHolderBuilding(v);
            else if (layout == R.layout.room_list_level_item)
                return new RoomListViewHolderLevel(v);
            else
                return new RoomListViewHolderRoom(v);
        };
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(factory);
        list.setAdapter(treeViewAdapter);

        treeViewAdapter.updateTreeNodes(RoomListConverter.printList(this));

        RoomListConverter.printList(this);
    }

    @Override
    public void onBackPressed() {
        System.out.println("User wants to go back from Room list");
        System.out.println("Navigating from Room List back to Main");
        finish();
    }

}
