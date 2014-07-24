package com.androideas.quakesnz.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.androideas.quakesnz.app.R;

/**
 * Created by Leandro on 24/07/2014.
 */
public class InfoActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int i) {
                int title, body;

                switch (i) {
                    case 0:
                        title = R.string.before_title;
                        body = R.string.before_body;
                        break;
                    case 1:
                        title = R.string.during_title;
                        body = R.string.during_body;
                        break;
                    case 2:
                    default:
                        title = R.string.after_title;
                        body = R.string.after_body;
                }

                return InfoFragment.newInstance(title, body);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mViewPager.setOnPageChangeListener(this);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText(R.string.before).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.during).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.after).setTabListener(this));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
