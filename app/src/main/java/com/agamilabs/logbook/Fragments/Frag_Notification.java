package com.agamilabs.logbook.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.agamilabs.logbook.Adapter.RV_Adapter_notification;
import com.agamilabs.logbook.Model.NotificationModel;
import com.agamilabs.logbook.R;

import java.util.ArrayList;

public class Frag_Notification extends Fragment {
    private ArrayList<NotificationModel> itemsList ;
    private RecyclerView recyclerView;
    private RV_Adapter_notification rv_adapter_notification;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_notification, container, false);
        recyclerView = view.findViewById(R.id.rv_notification_frag);

        recyclerViewHandler();
        return view;
    }

    private void recyclerViewHandler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_adapter_notification = new RV_Adapter_notification(getData(), getActivity());
        recyclerView.setAdapter(rv_adapter_notification);
    }

    private ArrayList<NotificationModel> getData() {

        int[] iconImage = {R.drawable.img1, R.drawable.img1, R.drawable.img1, R.drawable.img1,
                             R.drawable.img1, R.drawable.img1, R.drawable.img1, R.drawable.img1};
/*
        int[] iconImage = {R.drawable.notif_image1, R.drawable.notif_image2, R.drawable.notif_image3, R.drawable.notif_image4,
                             R.drawable.notif_image1, R.drawable.notif_image2, R.drawable.notif_image3, R.drawable.notif_image4};
*/
        String[] title = {"Notification 1", "Notification 2", "Notification 3", "Notification 4",
                            "Notification 5", "Notification 6", "Notification 7", "Notification 8"};
        String[] descShort = {"This is short description of notification 1", "This is short description of notification 1", "This is short description of notification 1", "This is short description of notification 1",
                "This is short description of notification 1", "This is short description of notification 1", "This is short description of notification 1", "This is short description of notification 1"};
        String[] time = {"30 minutes", "1 hour", "3 hours", "4 hours", "6 hours", "9 hours", "10 hours", "11 hours"};

        itemsList = new ArrayList<>();
        for (int i = 0; i<iconImage.length; i++)
        {
            NotificationModel current = new NotificationModel();

            current.iconImage = iconImage[i];
            current.title = title[i];
            current.descShort = descShort[i];
            current.time = time[i];

            itemsList.add(current);
        }

        return itemsList;
    }


}
