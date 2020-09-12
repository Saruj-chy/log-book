package com.agamilabs.logbook.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.agamilabs.logbook.MainActivity;
import com.agamilabs.logbook.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SecondFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    MainActivity activity ;
    View view ;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_second, container, false);

        if (Build.VERSION.SDK_INT >= 21)
        {
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorWhite));
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorSecondary));
        }//End

        toolbar = view.findViewById(R.id.toolbar_custom);
        bottomNavigationView = view.findViewById(R.id.nav_bottom);

//        view.getContext().setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);



        return  view ;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.homeItem:
                fragment = new Fragment_Mid();
                break;

            case R.id.notifyItem:
                fragment = new Frag_Notification();
                break;

            case R.id.settingItem:
                fragment = new Frag_settings();
                break;

            case R.id.profileItem:
                fragment = new Frag_profile();
                break;
        }
        return loadFragment(fragment);
    }
}