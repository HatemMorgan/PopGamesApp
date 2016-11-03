package com.example.hatem.popgames.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.popgames.Adapters.PS4GamesAdapter;
import com.example.hatem.popgames.ORM.Games;
import com.example.hatem.popgames.ORM.GamesCollection;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Playstation4_Fragment extends Fragment {

    private ArrayList<Games> gamesList;
    private Context context;
    private GridView pcGamesGridView;
    private TextView textView_gameName;

    public Playstation4_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_playstation4_, container, false);

        pcGamesGridView = (GridView) rootView.findViewById(R.id.gridview_PS4_Games);

        pcGamesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if(gamesList != null){

                    int gameID = gamesList.get(position).getId();
                    bundle.putInt("id",gameID);


                    new Toast(view.getContext()).makeText(view.getContext(),gameID+"",Toast.LENGTH_LONG).show();

                }


            }
        });



        return  rootView ;

    }

    @Override
    public void onStart() {
        super.onStart();

        updateGames();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("gamesList",gamesList);
    }


    public void updateGames() {
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FIELD_LIST_PARAM = "field_list";
        final String LIMIT_PARAM = "limit";
        final String FILTER_PARAM = "filter";
        String getGamesUrl = "http://www.giantbomb.com/api/games/";

        Uri buildUri = Uri.parse(getGamesUrl)
                .buildUpon()
                .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                .appendQueryParameter(FIELD_LIST_PARAM, "id,name,image")
                .appendQueryParameter(LIMIT_PARAM, getString(R.string.response_limit))
                .appendQueryParameter(FILTER_PARAM, getString(R.string.response_filter_PS4))
                .build();



        StringRequest getPS4GamesRequest = new StringRequest(Request.Method.GET, buildUri.toString(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        GamesCollection gamesCollection = gson.fromJson(response, GamesCollection.class);
                        gamesList = gamesCollection.getGamesResults();


                        PS4GamesAdapter ps4GamesAdapter = new PS4GamesAdapter(context, gamesList);
                        pcGamesGridView.setAdapter(ps4GamesAdapter);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).getmRequestQueue().cancelAll("TAG");
        RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(getPS4GamesRequest);



    }


}
