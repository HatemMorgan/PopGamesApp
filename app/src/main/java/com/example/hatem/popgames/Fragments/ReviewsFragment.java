package com.example.hatem.popgames.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hatem.popgames.R;
import com.squareup.picasso.Picasso;

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


    

}
