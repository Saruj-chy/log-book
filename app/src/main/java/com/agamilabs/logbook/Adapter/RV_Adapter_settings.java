package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.agamilabs.logbook.Model.SettingsModel;
import com.agamilabs.logbook.R;

import java.util.ArrayList;

public class RV_Adapter_settings extends RecyclerView.Adapter<RV_Adapter_settings.MyViewHolder> {
    private ArrayList<SettingsModel> itemList;
    private Context context;

    public RV_Adapter_settings(ArrayList<SettingsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_setting_frag, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SettingsModel current = itemList.get(position);

        if (current.getItems().equals("Dark Mode"))
        {
            holder.items.setText(current.getItems());
            holder.darkMode.setText("Off");
            holder.darkMode.setVisibility(View.VISIBLE);
        }
        else {
            holder.items.setText(current.getItems());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView items, darkMode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.tv_row_rv_setting_frag);
            darkMode = itemView.findViewById(R.id.tv_row_setting_frag_darkMode);
        }
    }
}
