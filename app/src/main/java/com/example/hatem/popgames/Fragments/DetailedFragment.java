package com.example.hatem.popgames.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hatem.popgames.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragment extends Fragment {

    private Gallery horizonatalListView ;
    private TextView textView_gameName;
    private TextView textView_year;
    private TextView textView_monthDay;
    private TextView textView_description;
    private ListView listView_videos;
    private Button btn_Reviews;
    private Button btn_Gallary;


    public DetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_detailed, container, false);

        horizonatalListView = (Gallery) rootView.findViewById(R.id.horizontal_list_view_similar_games);
        textView_gameName = (TextView) rootView.findViewById(R.id.textView_title);
        textView_description = (TextView) rootView.findViewById(R.id.textView_overview);
        textView_year = (TextView) rootView.findViewById(R.id.textView_year);
        textView_monthDay = (TextView) rootView.findViewById(R.id.textView_date);
        listView_videos = (ListView) rootView.findViewById(R.id.listView_videos);
        btn_Gallary = (Button) rootView.findViewById(R.id.btn_gallary);
        btn_Reviews = (Button) rootView.findViewById(R.id.btn_Reviews);

        return rootView ;


    }


    private void getData(){
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FIELD_LIST_PARAM = "field_list";
        String getGamesUrl = "http://www.giantbomb.com/api/games/";

        Uri buildUri = Uri.parse(getGamesUrl)
                .buildUpon()
                .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                .appendQueryParameter(FIELD_LIST_PARAM, "id,name,deck,original_release_date,image,images,reviews,,similar_games,videos")
                .build();


    }


}
