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
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;

import java.util.ArrayList;

public class DetailedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DetailedFragment detailed_fragment;
    private AppCompatActivity context ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initToolBar();
        context = this;
        detailed_fragment = new DetailedFragment() ;

        getSupportFragmentManager().beginTransaction().replace(R.id.DetialedGame_container,detailed_fragment).commit();




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
                Bundle bundle = context.getIntent().getExtras();
                boolean checkIFCalledFromMainActivity = bundle.getBoolean("FromMainActivity");
                Intent intent ;
                if(checkIFCalledFromMainActivity) {
                    intent = new Intent(v.getContext(),MainActivity.class);

                }else{
                    // count tell me if the previous activity was the main activity if I am comming from a long travel through detailed activity
                    // if count = 0 this mean that I will go to main activity else i will decrement by one and go to detailed activity
                    int count = bundle.getInt("counter");
                    if(count == 1){
                        intent = new Intent(v.getContext(),MainActivity.class);
                    }else{
                        count--;
                        intent = new Intent(v.getContext(),DetailedActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("counter",count);
                        ArrayList<Integer> prev = bundle.getIntegerArrayList("prevs");

                        if(count != 1)
                        b.putInt("PreviousGameID",prev.get(count-2));

                        b.putInt("game_id",prev.get(count-1));
                        b.putIntegerArrayList("prevs",prev);
                        intent.putExtras(b);
                    }


                }
                RequestQueueSingelton.getmInstance(context.getApplicationContext()).EmptyQueue();
                startActivity(intent);
                finish();
            }
        });

    }


}
