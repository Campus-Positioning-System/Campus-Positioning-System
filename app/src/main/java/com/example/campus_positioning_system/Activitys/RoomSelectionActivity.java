package com.example.campus_positioning_system.Activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.amrdeveloper.treeview.TreeNode;
import com.amrdeveloper.treeview.TreeViewAdapter;
import com.amrdeveloper.treeview.TreeViewHolderFactory;
import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.RoomList.RoomListConverter;
import com.example.campus_positioning_system.RoomList.RoomListViewHolder;

import java.util.ArrayList;
import java.util.List;





public class RoomSelectionActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_selection_activity);

        list = findViewById(R.id.room_list);
        list.setLayoutManager(new LinearLayoutManager(this));

        TreeViewHolderFactory factory = (v, layout) -> new RoomListViewHolder(v);
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(factory);
        list.setAdapter(treeViewAdapter);

        treeViewAdapter.updateTreeNodes(RoomListConverter.printList(this));

        RoomListConverter.printList(this);



    }
    @Override
    public void onBackPressed()
    {
        System.out.println("User wants to go back from Room list");

        NavHostFragment navHostFragment = (NavHostFragment) MainActivity.getSupportFragmentManagerMain().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.mainFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        MainActivity.setOnlyNavigateOnceTrue();

        System.out.println("Navigating from Room List back to Main");
    }

}
