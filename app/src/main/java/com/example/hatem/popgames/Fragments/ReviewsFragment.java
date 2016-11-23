package com.example.hatem.popgames.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    ImageView imageView_gameImage;
    TextView textView_gameName;
    TextView textView_gameReleaseYear;
    TextView textView_noReviews;
    ListView listView_review_list;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);

        imageView_gameImage = (ImageView) rootView.findViewById(R.id.imageView_Review_gameImage);
        textView_gameName = (TextView) rootView.findViewById(R.id.textView_Review_gameName);
        textView_gameReleaseYear = (TextView) rootView.findViewById(R.id.textView_review_gameReleaseYear);
        listView_review_list = (ListView) rootView.findViewById(R.id.listView_Reviews_list);
        textView_noReviews = (TextView) rootView.findViewById(R.id.textView_NoReviews);


        // getting passed data from bundle
        Bundle bundle = getActivity().getIntent().getExtras();
        String gameName = bundle.getString("gameName");
        String gameReleaseYear = bundle.getString("gameReleaseYear");
        String gameImageUrl = bundle.getString("gameImageUrl");

        // populating the view
        Picasso.with(getActivity()).load(gameImageUrl).placeholder(R.drawable.progress_animation).into(imageView_gameImage);
        textView_gameName.setText(gameName);
        textView_gameReleaseYear.setText(gameReleaseYear);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void getReviewFromApi (String gameID){
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FILTER_PARAM = "filter";
//        final String SORT_PARAM = "sort";
        final String FIELD_LIST_PARAM = "field_list";
        String getGamesUrl = "http://www.giantbomb.com/api/user_reviews";

        Uri buildUri = Uri.parse(getGamesUrl)
                        .buildUpon()
                        .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                        .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                        .appendQueryParameter(FIELD_LIST_PARAM, "reviewer,deck,score,site_detail_url")
                        .appendQueryParameter(FILTER_PARAM,"game:"+gameID)
                        .build();

        JsonObjectRequest getReviewsRequest = new JsonObjectRequest(Request.Method.GET, buildUri.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");

                    for(int i =0 ;i<result.length();i++) {
                        JSONObject singleReview = result.getJSONObject(i);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingelton.getmInstance(this).addToRequestQueue(getReviewsRequest);

    }


}
