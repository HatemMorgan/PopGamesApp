package com.example.hatem.popgames.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hatem.popgames.Fragments.DetailedFragment;
import com.example.hatem.popgames.R;

public class DetailedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DetailedFragment detailed_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initToolBar();

        detailed_fragment = new DetailedFragment() ;

        getSupportFragmentManager().beginTransaction().replace(R.id.DetialedMovies_container,detailed_fragment).commit();




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


    private void initToolBar (){
        toolbar = (Toolbar) findViewById(R.id.DetailedTtoolbar);
        toolbar.setTitle(R.string.detailed_activity_name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
