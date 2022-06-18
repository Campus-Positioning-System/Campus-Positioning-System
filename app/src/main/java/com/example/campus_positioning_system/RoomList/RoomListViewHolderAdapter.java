package com.example.campus_positioning_system.RoomList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_positioning_system.NNObject;
import com.example.campus_positioning_system.R;

import java.util.List;

public class RoomListViewHolderAdapter extends RecyclerView.Adapter<RoomListViewHolder>{

    Context context;
    List<NNObject> list;

    // constructor with Record's data model list and view context
    public RoomListViewHolderAdapter(Context context, List<NNObject> list){
        this.context = context;
        this.list = list;
    }

    // Binding data to the
    // into specified position
    @NonNull
    @Override
    public RoomListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the recycler view
        // layout in its view component
        View v = LayoutInflater.from(context).inflate(R.layout.room_item,parent,false);
        return new RoomListViewHolder(v);
    }

    // holder method to set respective
    // data to their components
    @Override
    public void onBindViewHolder(@NonNull RoomListViewHolder holder, int position) {
        if(list == null)
            throw new RuntimeException("List was empty");
        NNObject user = list.get(position);
        holder.name.setText(Integer.toString(user.getLocation().getX())); // user.getLocation().getX()
        holder.attempted.setText(Integer.toString(user.getLocation().getY())); // user.getLocation().getY()
        holder.correct.setText(Integer.toString(user.getLocation().getZ())); // user.getLocation().getZ()
        holder.time.setText(user.getMac()); // user.getMac()
    }

    // method to return the
    // position of the item
    @Override
    public int getItemCount() {
        return list.size();
    }
}
