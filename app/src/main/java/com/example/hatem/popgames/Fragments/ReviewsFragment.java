package com.example.hatem.popgames.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.popgames.Adapters.ReviewsAdapter;
import com.example.hatem.popgames.ORM.Review;
import com.example.hatem.popgames.ORM.ReviewsAPIObject;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    ImageView imageView_gameImage;
    TextView textView_gameName;
    TextView textView_gameReleaseYear;
    TextView textView_noReviews;
    ListView listView_review_list;
    Context context;

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


        listView_review_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ImageView image = (ImageView) view.findViewById(R.id.imageView_Review_AuthorImage);
                    String reviewSiteURL = image.getContentDescription().toString();
                    // create explicit intent to allow navigating the user to site of the full review throgh the browser
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(reviewSiteURL));

                startActivity(webIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        Bundle bundle = getActivity().getIntent().getExtras();
        int gameID = bundle.getInt("gameID");
        getReviewFromApi(gameID);
    }

    private void getReviewFromApi (int gameID){
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

        StringRequest getReviewsRequest = new StringRequest(Request.Method.GET, buildUri.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson  = new Gson();
                ReviewsAPIObject  reviewsAPiResponseObject = gson.fromJson(response,ReviewsAPIObject.class);
                List<Review> reviewsList = reviewsAPiResponseObject.getResults();
                if(reviewsList.size()>0) {
                    ReviewsAdapter reviewsAdapter = new ReviewsAdapter(context, reviewsList);
                    listView_review_list.setAdapter(reviewsAdapter);
                    setListViewHeightBasedOnChildren(listView_review_list);
                }else {
                    textView_noReviews.setText("Sorry No Reviews for this game ");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueueSingelton.getmInstance(getActivity()).addToRequestQueue(getReviewsRequest);

    }


    // used to set the hieght of the list view based on the children when adding a listview to scroll view
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
