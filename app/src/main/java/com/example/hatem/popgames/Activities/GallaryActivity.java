package com.example.hatem.popgames.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hatem.popgames.Adapters.ImagesAdapter;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GallaryActivity extends AppCompatActivity {
    private  Gallery gallaryView ;
    private ImageView imageViewGallary ;
    private Context context ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);

        context = this;
        initToolBar();
        gallaryView = (Gallery) findViewById(R.id.gallary);
        imageViewGallary = (ImageView) findViewById(R.id.imageView_gallary);

        Bundle bundle = this.getIntent().getExtras();
        int gameID = bundle.getInt("game_id");

        getImages(gameID);

        gallaryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView selectedImage = (ImageView) view;
                String imageUrl = selectedImage.getContentDescription().toString();
                Picasso.with(context).load(imageUrl).resize(250,350).placeholder(R.drawable.progress_animation).into(imageViewGallary);
            }
        });



    }

    private void getImages(int gameID){
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FIELD_LIST_PARAM = "field_list";
        String getGamesUrl = "http://www.giantbomb.com/api/game/"+gameID+"/";

        Uri buildUri = Uri.parse(getGamesUrl)
                          .buildUpon()
                          .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                          .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                          .appendQueryParameter(FIELD_LIST_PARAM, "images")
                          .build();

        JsonObjectRequest getImagesRequest = new JsonObjectRequest(Request.Method.GET, buildUri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("results");
                            JSONArray imagesArray = result.getJSONArray("images");
                            ArrayList<String> thumbImagesUrlList = new ArrayList<>();
                            ArrayList<String> mediumImagesUrlList = new ArrayList<>();
                            for (int i=0 ; i < imagesArray.length();i++){
                                JSONObject image = imagesArray.getJSONObject(i);
                                String thumbImageUrl = image.getString("thumb_url");
                                String mediumImageUrl = image.getString("medium_url");

                                thumbImagesUrlList.add(thumbImageUrl);
                                mediumImagesUrlList.add(mediumImageUrl);
                            }

                            ImagesAdapter imagesAdapter = new ImagesAdapter(context,thumbImagesUrlList,mediumImagesUrlList);
                            gallaryView.setAdapter(imagesAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingelton.getmInstance(this).addToRequestQueue(getImagesRequest);
    }

    private void initToolBar (){
        toolbar = (Toolbar) findViewById(R.id.gallaryTtoolbar);
        toolbar.setTitle("Galary");

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
