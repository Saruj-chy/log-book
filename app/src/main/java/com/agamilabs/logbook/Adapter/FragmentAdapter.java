package com.agamilabs.logbook.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.agamilabs.logbook.Fragments.SettingsFragment;
import com.agamilabs.logbook.Fragments.SecondFragment;
import com.agamilabs.logbook.Fragments.ThirdFragment;
import com.agamilabs.logbook.MainActivity;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs1;
    private MainActivity mActivity;

    public FragmentAdapter(FragmentManager fm, int NumOfTabs1, MainActivity activity) {
        super(fm);
        this.mNumOfTabs1 = NumOfTabs1;
        this.mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SettingsFragment tab0 = new SettingsFragment() ;
                return tab0;
            case 1:
                SecondFragment tab1 = new SecondFragment(mActivity) ;
                return tab1;
            case 2:
                ThirdFragment tab2 = new ThirdFragment() ;
                return tab2;
            default:
                return null ;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs1;
    }
}