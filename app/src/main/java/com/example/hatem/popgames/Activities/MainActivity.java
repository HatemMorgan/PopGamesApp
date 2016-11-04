package com.example.hatem.popgames.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hatem.popgames.Adapters.ViewPagerAdapter;
import com.example.hatem.popgames.Fragments.PC_Fragment;
import com.example.hatem.popgames.Fragments.Playstation3_Fragment;
import com.example.hatem.popgames.Fragments.Playstation4_Fragment;
import com.example.hatem.popgames.Fragments.XBox_Fragment;
import com.example.hatem.popgames.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing  toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pop Games");
        setSupportActionBar(toolbar);

        // initializing viewPager and pagerAdapter
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        // initializing tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PC_Fragment(), "PC");
        adapter.addFragment(new Playstation3_Fragment(), "PS3");
        adapter.addFragment(new Playstation4_Fragment(), "PS4");
        adapter.addFragment(new XBox_Fragment(), "XBOX");
        viewPager.setAdapter(adapter);
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
