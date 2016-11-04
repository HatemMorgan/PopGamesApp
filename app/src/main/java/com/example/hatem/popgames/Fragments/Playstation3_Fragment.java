package com.example.hatem.popgames.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.popgames.Adapters.GamesAdapter;
import com.example.hatem.popgames.ORM.Games;
import com.example.hatem.popgames.ORM.GamesCollection;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Playstation3_Fragment extends Fragment {

    private ArrayList<Games> gamesList;
    private Context context;
    private GridView pcGamesGridView;
    private SharedPreferences sharedPreferences ;

    public Playstation3_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        // intialize shared preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_playstation3_, container, false);

        pcGamesGridView = (GridView) rootView.findViewById(R.id.gridview_PS3_Games);

        pcGamesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (gamesList != null) {

                    int gameID = gamesList.get(position).getId();
                    bundle.putInt("id", gameID);


                    new Toast(view.getContext()).makeText(view.getContext(), gameID + "", Toast.LENGTH_LONG).show();

                }


            }
        });


        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
            updateGames();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("gamesList", gamesList);
    }


    public void updateGames() {
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FIELD_LIST_PARAM = "field_list";
        final String LIMIT_PARAM = "limit";
        final String FILTER_PARAM = "filter";
        final String SORT_PARAM =  "sort";
        String getGamesUrl = "http://www.giantbomb.com/api/games/";

        String  sortCriteria = sharedPreferences.getString(getString(R.string.pref_sort_list_key), getString(R.string.sort_by_release_date));


        Uri buildUri = Uri.parse(getGamesUrl)
                .buildUpon()
                .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                .appendQueryParameter(FIELD_LIST_PARAM, "id,name,image")
                .appendQueryParameter(LIMIT_PARAM, getString(R.string.response_limit))
                .appendQueryParameter(FILTER_PARAM, getString(R.string.response_filter_PS3))
                .appendQueryParameter(SORT_PARAM,sortCriteria+":desc")
                .build();

        StringRequest getPS3GamesRequest = new StringRequest(Request.Method.GET, buildUri.toString(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        GamesCollection gamesCollection = gson.fromJson(response, GamesCollection.class);
                        gamesList = gamesCollection.getGamesResults();

                        GamesAdapter ps3GamesAdapter = new GamesAdapter(context, gamesList);
                        pcGamesGridView.setAdapter(ps3GamesAdapter);

//                        RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).getmRequestQueue().cancelAll("TAG");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                    }
                }
        );

                RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(getPS3GamesRequest);



    }

}