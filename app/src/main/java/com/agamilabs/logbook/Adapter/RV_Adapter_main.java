package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.agamilabs.logbook.Interfaces.Interface_1;
import com.agamilabs.logbook.Model.Main_Model;
import com.agamilabs.logbook.R;

import java.util.ArrayList;

public class RV_Adapter_main extends RecyclerView.Adapter<RV_Adapter_main.ViewHolder1> {
    private static final String TAG = "RV_Adapter_main";
    private ArrayList<Main_Model> myList;
    private Context context;
    private String preGroupName = null;
    private Interface_1 interface_1;

    public RV_Adapter_main(ArrayList<Main_Model> myList, Context context, Interface_1 interface_1) {
        this.myList = myList;
        this.context = context;
        this.interface_1 = interface_1;
    }


    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_mid_frag_main, parent, false);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        Main_Model current = myList.get(position);
        if (preGroupName == null)
        {
            holder.groupName.setVisibility(View.VISIBLE);
            holder.groupName.setText(current.getGroup());
            holder.itemName.setText(current.getItem());
            holder.itemId.setText(current.getItem_id());
            holder.groupId.setText(current.getGroup_id());
            preGroupName = current.getGroup();
            holder.groupName.setPadding(10, 5, 0, 5);
        }
        else if (current.getGroup().equals(preGroupName))
        {
            holder.groupName.setVisibility(View.GONE);
            holder.itemName.setText(current.getItem());
            holder.itemId.setText(current.getItem_id());
            holder.groupId.setText(current.getGroup_id());

        }
        else if (!current.getGroup().equals(preGroupName))
        {
            holder.groupName.setVisibility(View.VISIBLE);
            holder.groupName.setText(current.getGroup());
            holder.itemName.setText(current.getItem());
            holder.itemId.setText(current.getItem_id());
            holder.groupId.setText(current.getGroup_id());
            preGroupName = current.getGroup();
        }

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/

    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groupName;
        TextView itemName;
        TextView itemId;
        TextView groupId;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.tv_rv_main_heading);
            itemName = itemView.findViewById(R.id.tv_items);
            itemId = itemView.findViewById(R.id.tv_item_id);
            groupId = itemView.findViewById(R.id.tv_group_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int currentPos = getAdapterPosition();

            Main_Model current = myList.get(currentPos);

            String group_id = current.getGroup_id();
            String group = current.getGroup();
            String item_id = current.getItem_id();
            String item = current.getItem();

            Log.d(TAG, "onClick: "+ group_id+ " "+ group+ " "+ item_id+ " "+ item);
            interface_1.clickOnRvMain(group_id, group, item_id, item);
        }
    }




}
