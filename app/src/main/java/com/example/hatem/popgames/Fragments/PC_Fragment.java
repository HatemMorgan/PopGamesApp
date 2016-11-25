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
public class PC_Fragment extends Fragment {

    private ArrayList<Games> gamesList ;
    private Context context ;
    private GridView pcGamesGridView ;
    private SharedPreferences sharedPreferences ;
    private  GamesAdapter GamesAdapter;
    private ProgressBar loadingSpinnerView ;

    public PC_Fragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_pc_, container, false);

        loadingSpinnerView = (ProgressBar) rootView.findViewById(R.id.progressBar_PC);
        pcGamesGridView = (GridView) rootView.findViewById(R.id.gridview_PC_Games);

        pcGamesGridView.setVisibility(View.GONE);
        loadingSpinnerView.setVisibility(View.VISIBLE);

        pcGamesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if(gamesList != null){

                    int gameID = gamesList.get(position).getId();
                   ArrayList<Integer> prev = new ArrayList<Integer>();
                    prev.add(gameID);
                    bundle.putInt("game_id",gameID);
//                    bundle.putBoolean("FromMainActivity",true);
                    bundle.putInt("counter",1);
                    bundle.putIntegerArrayList("prevs",prev);
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
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState == null) {
            updateGames();
        }else{
            if(savedInstanceState.getSerializable("PC_Games") != null) {
                loadingSpinnerView.setVisibility(View.GONE);
                pcGamesGridView.setVisibility(View.VISIBLE);
                gamesList = (ArrayList<Games>) savedInstanceState.getSerializable("PC_Games");

                GamesAdapter GamesAdapter = new GamesAdapter(context, gamesList);
                pcGamesGridView.setAdapter(GamesAdapter);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("PC_Games",gamesList);
    }

    public  void updateGames() {
        GetPcGamesTask getPcGamesTask = new GetPcGamesTask();
        getPcGamesTask.execute();

    }

    private class GetPcGamesTask extends AsyncTask<URL, Integer, ArrayList<Games>> {

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
                        .appendQueryParameter(FILTER_PARAM, getString(R.string.response_filter_PC))
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


