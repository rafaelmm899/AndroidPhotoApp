package com.softmedialtda.softmediaphotoapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.softmedialtda.softmediaphotoapp.R;

import layout.ListNotificationFrag;
import layout.NotificationFrag;

/**
 * Created by Agustin on 12/4/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NotificationFrag.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Todo";
            case 1:
                return "Vistos";
            case 2:
                return "Sin leer";
        }
        return null;
    }
}
