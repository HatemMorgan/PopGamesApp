package com.example.hatem.popgames.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hatem.popgames.Fragments.ReviewsFragment;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;

public class ReviewsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ReviewsFragment reviewsFragment ;
    private AppCompatActivity context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        context = this;
        initToolBar();

        reviewsFragment = new ReviewsFragment() ;

        getSupportFragmentManager().beginTransaction().replace(R.id.reviews_container,reviewsFragment).commit();


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
        toolbar = (Toolbar) findViewById(R.id.ReviewsTtoolbar);
        toolbar.setTitle("Reviews");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = context.getIntent().getExtras();
                Intent intent = new Intent(v.getContext(),DetailedActivity.class);
                intent.putExtras(bundle);
                RequestQueueSingelton.getmInstance(context.getApplicationContext()).EmptyQueue();
                startActivity(intent);
                finish();
            }
        });

    }

}
