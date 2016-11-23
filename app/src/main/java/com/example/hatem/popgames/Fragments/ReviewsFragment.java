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

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    ImageView imageView_moviePoster;
    TextView textView_movieTitle;
    TextView textView_movieReleaseYear;
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

        imageView_moviePoster = (ImageView) rootView.findViewById(R.id.imageView_Review_moviePoster);
        textView_movieTitle = (TextView) rootView.findViewById(R.id.textView_Review_movieTitle);
        textView_movieReleaseYear = (TextView) rootView.findViewById(R.id.textView_review_movieYeat);
        listView_review_list = (ListView) rootView.findViewById(R.id.listView_Reviews_list);
        textView_noReviews = (TextView) rootView.findViewById(R.id.textView_NoReviews);

        Bundle bundle = getActivity().getIntent().getExtras();



        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
