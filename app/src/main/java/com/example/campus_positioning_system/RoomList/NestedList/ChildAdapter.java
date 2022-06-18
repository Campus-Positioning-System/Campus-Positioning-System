package com.example.campus_positioning_system.RoomList.NestedList;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.campus_positioning_system.R;
import com.example.campus_positioning_system.databinding.FragmentMainBinding;

import java.util.ArrayList;



public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Child> childData;
    private ArrayList<Child> childDataBk;
    private FragmentMainBinding binding;

    public ChildAdapter(ArrayList<Child> childData) {
        this.childData = childData;
        childDataBk = new ArrayList<>(childData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

         TextView tvChild;
        ImageView tvExpandCollapseToggle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExpandCollapseToggle = itemView.findViewById(R.id.iv_expand_collapse_toggle);
            tvChild = itemView.findViewById(R.id.tv_child);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_recycler_item_child, parent, false);

        ChildAdapter.ViewHolder cavh = new ChildAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }


    final Handler handler = new Handler();
    Runnable collapseList = new Runnable() {
        @Override
        public void run() {
            if (getItemCount() > 1) {
                childData.remove(1);
                notifyDataSetChanged();
                handler.postDelayed(collapseList, 50);
            }
        }
    };

    Runnable expandList = new Runnable() {
        @Override
        public void run() {
            int currSize = childData.size();
            if (currSize == childDataBk.size()) return;

            if (currSize == 0) {
                childData.add(childDataBk.get(currSize));
            } else {
                childData.add(childDataBk.get(currSize));
            }
            notifyDataSetChanged();

            handler.postDelayed(expandList, 50);
        }
    };


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;

        if (position == 0 && getItemCount() == 1) {
            //vh.tvExpandCollapseToggle.setImageResource(R.drawable.ic_expand_more_white_24dp);
            vh.tvExpandCollapseToggle.setVisibility(View.VISIBLE);
        } else if (position == childDataBk.size() - 1) {
            //vh.tvExpandCollapseToggle.setImageResource(R.drawable.ic_expand_less_white_24dp);
            vh.tvExpandCollapseToggle.setVisibility(View.VISIBLE);
        } else {
            vh.tvExpandCollapseToggle.setVisibility(View.GONE);
        }

        Child c = childData.get(position);
        vh.tvChild.setText(c.getChild_name());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemCount() > 1) {
                    handler.post(collapseList);
                } else {
                    handler.post(expandList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childData.size();
    }
}
