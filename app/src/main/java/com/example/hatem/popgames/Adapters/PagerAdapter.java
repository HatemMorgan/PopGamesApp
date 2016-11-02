package com.example.hatem.popgames.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hatem.popgames.Fragments.PC_Fragment;
import com.example.hatem.popgames.Fragments.Playstation3_Fragment;
import com.example.hatem.popgames.Fragments.Playstation4_Fragment;
import com.example.hatem.popgames.Fragments.XBox_Fragment;


/**
 * Created by hatem on 11/2/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public PagerAdapter (FragmentManager fragmentManager , int numberOfTabs){
        super(fragmentManager);
        this.numberOfTabs = numberOfTabs ;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 1 :
            PC_Fragment  pc_fragment = new PC_Fragment();
                return pc_fragment ;
            case 2 :
                Playstation3_Fragment playstation3_fragment = new Playstation3_Fragment() ;
                return playstation3_fragment ;
            case 3 :
                Playstation4_Fragment playstation4_fragment = new Playstation4_Fragment() ;
                return  playstation4_fragment;
            case 4 :
                XBox_Fragment xBox_fragment = new XBox_Fragment() ;
                return  xBox_fragment ;
            default:
                return new PC_Fragment();

        }

    }

    @Override
    public int getCount() {
        return  numberOfTabs ;
    }
}
