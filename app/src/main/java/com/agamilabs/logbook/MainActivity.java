package com.agamilabs.logbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.agamilabs.logbook.Adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mTabLayout.addTab(mTabLayout.newTab().setText("Fragment 1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Fragment 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Fragment 3"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setVisibility(View.GONE);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), MainActivity.this );
        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(1);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    mViewPager.setPadding(0, 0, 100, 0) ;
                    mViewPager.setClipToPadding(false) ;
                }
                else if(position==1){
                    mViewPager.setPadding(0, 0, 0, 0) ;
                    mViewPager.setClipToPadding(false) ;
                }
                else if(position==2){
                    mViewPager.setPadding(0, 0, 0, 0) ;
                    mViewPager.setClipToPadding(false)  ;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelect(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void setSelect(int position){
        mViewPager.setCurrentItem(position);
        mViewPager.setPadding(0, 0, 0, 0);
        mViewPager.setClipToPadding(false);

    }
}