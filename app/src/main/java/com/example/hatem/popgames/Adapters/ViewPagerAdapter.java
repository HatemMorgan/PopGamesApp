package com.example.hatem.popgames.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


/**
 * Created by hatem on 11/2/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fragmentManager ){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
            return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public String getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
