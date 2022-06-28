package com.example.campus_positioning_system.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amrdeveloper.treeview.TreeViewAdapter;
import com.amrdeveloper.treeview.TreeViewHolderFactory;
import com.example.campus_positioning_system.Fragments.SettingsFragment;
import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.RoomList.RoomListConverter;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderBuilding;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderLevel;
import com.example.campus_positioning_system.RoomList.RoomListViewHolderRoom;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites_list);
        RoomListConverter.setMyContext(MainActivity.mainContext());

        list = findViewById(R.id.favorites_recycler_view);
        list.setLayoutManager(new LinearLayoutManager(this));

        TreeViewHolderFactory factory = (v, layout) -> {
            if (layout == R.layout.room_list_building_item)
                return new RoomListViewHolderBuilding(v);
            else if (layout == R.layout.room_list_level_item)
                return new RoomListViewHolderLevel(v);
            else
                return new RoomListViewHolderRoom(v,null);
        };
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(factory);
        list.setAdapter(treeViewAdapter);

        treeViewAdapter.updateTreeNodes(RoomListConverter.getFavoritesAsNodes());

        RoomListConverter.printList(this);

    }

    @Override
    public void onBackPressed() {
        System.out.println("User wants to go back from Room list");
        System.out.println("Navigating from Room List back to Main");
        finish();
    }
}
