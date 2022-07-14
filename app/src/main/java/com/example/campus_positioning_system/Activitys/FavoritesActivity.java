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

/**
 * Displays a List of favorites
 * @author Ben Lutz
 * @since 1.0
 */

public class FavoritesActivity extends AppCompatActivity {

    /**
     * List the Favorites will be displayed in
     */
    RecyclerView list;

    /**
     * Creates the favorites list onCreate of the FavoritesActivity
     * @param savedInstanceState State to restore if there is one
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites_list);
        RoomListConverter.setMyContext(MainActivity.mainContext());

        list = findViewById(R.id.favorites_recycler_view);
        list.setLayoutManager(new LinearLayoutManager(this));

        TreeViewHolderFactory factory = (v, layout) -> new RoomListViewHolderRoom(v,this);
        TreeViewAdapter listadapter = new TreeViewAdapter(factory);
        list.setAdapter(listadapter);

        listadapter.updateTreeNodes(RoomListConverter.getFavoritesAsNodes());

        //RoomListConverter.printList(this);

    }

    /**
     * Handles going back to the map when the user presses the back button
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}
