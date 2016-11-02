package com.example.hatem.popgames.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hatem.popgames.Adapters.PagerAdapter;
import com.example.hatem.popgames.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing  toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pop Games");
        setSupportActionBar(toolbar);

        // initializing tabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.PC_tab_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Playstation3_tab_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Playstation4_tab_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.XBox_tab_name));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // initializing viewPager and pagerAdapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
         PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // add option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true ;
    }

    // add actions when menu option items selected

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            System.out.print("Settings");
        }
        return super.onOptionsItemSelected(item);
    }
}
