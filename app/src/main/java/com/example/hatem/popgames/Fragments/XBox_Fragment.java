package com.example.hatem.popgames.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;

import com.example.hatem.popgames.Activities.DetailedActivity;
import com.example.hatem.popgames.Adapters.GamesAdapter;
import com.example.hatem.popgames.ORM.Games;
import com.example.hatem.popgames.ORM.GamesCollection;
import com.example.hatem.popgames.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class XBox_Fragment extends Fragment {

    private ArrayList<Games> gamesList;
    private Context context;
    private GridView pcGamesGridView;
    private SharedPreferences sharedPreferences;

    private  GamesAdapter GamesAdapter;
    private ProgressBar loadingSpinnerView ;


    public XBox_Fragment() {
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

        context = getActivity();

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_xbox_, container, false);

        loadingSpinnerView = (ProgressBar) rootView.findViewById(R.id.progressBar_XBOX);
        pcGamesGridView = (GridView) rootView.findViewById(R.id.gridview_xbox_Games);

        pcGamesGridView.setVisibility(View.GONE);
        loadingSpinnerView.setVisibility(View.VISIBLE);

        pcGamesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if(gamesList != null){

                    int gameID = gamesList.get(position).getId();
                    bundle.putInt("game_id",gameID);
                    Intent intent = new Intent(getActivity(), DetailedActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                    getActivity().finish();

                }


            }
        });



        return  rootView ;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            updateGames();
        }else{
            if(savedInstanceState.getSerializable("XBOX_Games") != null) {
                gamesList = (ArrayList<Games>) savedInstanceState.getSerializable("XBOX_Games");

                GamesAdapter GamesAdapter = new GamesAdapter(context, gamesList);
                pcGamesGridView.setAdapter(GamesAdapter);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("XBOX_Games",gamesList);
    }

    public void updateGames() {

        GetXBOXGamesTask getXBOXGamesTask= new GetXBOXGamesTask();
        getXBOXGamesTask.execute();



    }

    private class GetXBOXGamesTask extends AsyncTask<URL, Integer, ArrayList<Games>> {

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(ArrayList<Games> result) {
            loadingSpinnerView.setVisibility(View.GONE);
            pcGamesGridView.setVisibility(View.VISIBLE);
            if (result != null) {
                GamesAdapter = new GamesAdapter(context, result);
                pcGamesGridView.setAdapter(GamesAdapter);
            }
        }


        protected ArrayList<Games> doInBackground(URL... urls) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String PCGamesJsonString = null;
            try {
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
                        .appendQueryParameter(FILTER_PARAM, getString(R.string.response_filter_XBOX))
                        .appendQueryParameter(SORT_PARAM,sortCriteria+":desc")
                        .build();


                URL url = new URL(buildUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                PCGamesJsonString = buffer.toString();

                Gson gson = new Gson();
                GamesCollection gamesCollection = gson.fromJson(PCGamesJsonString, GamesCollection.class);
                gamesList = gamesCollection.getGamesResults();


            } catch (IOException e) {
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Error", "Error closing stream", e);
                    }
                }
                return  gamesList;
            }

        }

    }


}
