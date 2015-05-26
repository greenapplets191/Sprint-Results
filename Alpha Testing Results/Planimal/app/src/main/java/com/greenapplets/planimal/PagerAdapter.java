package com.greenapplets.planimal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ASUS on 3/14/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0: fragment = new FragmentPet(); break;
            case 1: fragment = new FragmentSchedule(); break;
            case 2: fragment = new FragmentShop(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
