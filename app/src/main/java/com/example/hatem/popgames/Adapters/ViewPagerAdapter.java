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
//                Fragment targetFragment ;
//        switch (position){
////            case 0:
////                PC_Fragment  pc_fragment1 = new PC_Fragment();
////                return  pc_fragment1;
//            case 0 :
//                    targetFragment =mFragmentList.get(0);
//                ((PC_Fragment)targetFragment).updateGames();
//                return targetFragment;
//            case 1 :
//                targetFragment =mFragmentList.get(1);
//                ((Playstation3_Fragment)targetFragment).updateGames();
//                return  targetFragment;
//            case 2 :
//                targetFragment =mFragmentList.get(2);
//                ((Playstation4_Fragment)targetFragment).updateGames();
//                return  targetFragment;
//            case 3 :
//                targetFragment =mFragmentList.get(3);
//                ((XBox_Fragment)targetFragment).updateGames();
//                return targetFragment;
//
//            default: return  null ;

//        }


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
