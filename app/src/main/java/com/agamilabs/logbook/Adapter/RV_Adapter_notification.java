package com.agamilabs.logbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agamilabs.logbook.Model.NotificationModel;
import com.agamilabs.logbook.R;

import java.util.ArrayList;

public class RV_Adapter_notification extends RecyclerView.Adapter<RV_Adapter_notification.MyViewHolder> {
    private ArrayList<NotificationModel> itemList;
    private Context context;

    public RV_Adapter_notification(ArrayList<NotificationModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_notificatin_frag, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel current = itemList.get(position);
        holder.iconImage.setImageResource(current.getIconImage());
        holder.title.setText(current.getTitle());
        holder.descShort.setText(current.getDescShort());
        holder.time.setText(current.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView title;
        TextView descShort;
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iconImage = itemView.findViewById(R.id.imgV_notificationIcon);
            title = itemView.findViewById(R.id.tv_notification_title);
            descShort = itemView.findViewById(R.id.tv_notification_short_desc);
            time = itemView.findViewById(R.id.tv_notification_date);
        }
    }
}
