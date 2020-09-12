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

import com.example.viewpager2.Adapters.RV_Adapter_settings;
import com.example.viewpager2.Model.SettingsModel;
import com.example.viewpager2.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Frag_settings extends Fragment {
    private ArrayList<SettingsModel> itemsList ;
    private RecyclerView recyclerView;
    private RV_Adapter_settings rv_adapter_settings;
    private SettingsModel settingsModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_setting, container, false);
        recyclerView = view.findViewById(R.id.rv_setting_frag);

            recyclerViewHandler();

        return view;
    }

    private void recyclerViewHandler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_adapter_settings = new RV_Adapter_settings(getData(), getActivity());
        recyclerView.setAdapter(rv_adapter_settings);
    }

    private ArrayList<SettingsModel> getData() {
        String[] itemsArray = {"Language & regions", "Advanced", "Dark Mode", "Help center", "Privacy & licenses"};
        itemsList = new ArrayList<>();

        for (int i = 0; i < itemsArray.length; i++) {
            SettingsModel settingsModel1 = new SettingsModel();
            Field[] fields = settingsModel1.getAllFields();
            String s = itemsArray[i];

            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                field.setAccessible(true);
                try {
                    field.set(settingsModel1, s);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            itemsList.add(settingsModel1);
        }

/*
       for (int i = 0; i<itemsArray.length; i++)
       {
           SettingsModel settingsModel = new SettingsModel();

           settingsModel.items = itemsArray[i];
           itemsList.add(settingsModel);
       }
*/

        return itemsList;
    }
}
