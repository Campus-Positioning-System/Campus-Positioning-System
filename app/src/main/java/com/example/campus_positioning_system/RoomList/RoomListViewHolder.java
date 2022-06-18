package com.example.campus_positioning_system.RoomList;


import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_positioning_system.R;


public class RoomListViewHolder extends RecyclerView.ViewHolder {

    // data variables to link with
    // the respective id from the view
    TextView name, attempted, correct, time;
    //ExpandableListView building, level,rooms;
    //TextView buildingLabel, levelLabel;

    public RoomListViewHolder(@NonNull View itemView) {
        super(itemView);
        /*
        building = itemView.findViewById(R.id.buildingSelectExpendableList);
        level = itemView.findViewById(R.id.levelSelectExpandableList);
        rooms = itemView.findViewById(R.id.roomSelectExpandableList);

        buildingLabel = itemView.findViewById(R.id.buildingListItem);
        levelLabel = itemView.findViewById(R.id.levelSelectListItem);*/

        name = itemView.findViewById(R.id.userName);
        attempted = itemView.findViewById(R.id.attempted);
        correct = itemView.findViewById(R.id.correct);
        time = itemView.findViewById(R.id.time);

    }
}
