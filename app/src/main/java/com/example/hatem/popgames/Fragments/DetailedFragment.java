package com.example.hatem.popgames.Fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.popgames.Adapters.SimilarGamesAdapter;
import com.example.hatem.popgames.Adapters.VideosAdapter;
import com.example.hatem.popgames.ORM.DetailedGame;
import com.example.hatem.popgames.ORM.SimilarGame;
import com.example.hatem.popgames.ORM.SingleGame;
import com.example.hatem.popgames.ORM.Video;
import com.example.hatem.popgames.ORM.VideoRespnse;
import com.example.hatem.popgames.ORM.YouTubeVideo;
import com.example.hatem.popgames.R;
import com.example.hatem.popgames.Utilities.DateUtility;
import com.example.hatem.popgames.Utilities.RequestQueueSingelton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    private ImageView gameImage;

    private ArrayList<SingleGame> similarGamesList;
    private ArrayList<String> youtubeVideoList;
    private Context context;



    public DetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        similarGamesList = new ArrayList<>();
        youtubeVideoList = new ArrayList<>();
        context = getActivity();
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
        gameImage = (ImageView) rootView.findViewById(R.id.imageView_poaster);

        return rootView ;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        int game_id = bundle.getInt("game_id");
        new Toast(context).makeText(context,game_id+"",Toast.LENGTH_LONG).show();
        getData(game_id+"");



    }

    public ArrayList<SingleGame> getSimilarGamesList() {
        return similarGamesList;
    }

    public ArrayList<String> getYoutubeVideoList() {
        return youtubeVideoList;
    }



    private void getData(String id){
        final String APPID_PARAM = "api_key";
        final String FORMAT_PARAM = "format";
        final String FIELD_LIST_PARAM = "field_list";
        String getGamesUrl = "http://www.giantbomb.com/api/game/"+id+"/";

        Uri buildUri = Uri.parse(getGamesUrl)
                .buildUpon()
                .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                .appendQueryParameter(FIELD_LIST_PARAM, "id,name,deck,original_release_date,image,images,reviews,,similar_games,videos")
                .build();

        StringRequest getPCGamesRequest = new StringRequest(Request.Method.GET, buildUri.toString(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        DetailedGame detailedGame = gson.fromJson(response, DetailedGame.class);

                        SingleGame game = detailedGame.getSingleGame();

                            populateView(game);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.toString());
                    }
                }
        );


        RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(getPCGamesRequest);
    }



    private void populateView(SingleGame game){

        textView_gameName.setText(game.getName());
        textView_description.setText(game.getDeck());

        String date = game.getOriginalReleaseDate();
        String year = DateUtility.getFormattedyear(date);
        String monthDay = DateUtility.getFormattedMonthDay(date);

        textView_year.setText(year);
        textView_monthDay.setText(monthDay);

        if(game.getImage() != null) {
            String imageURl = game.getImage().getSmallUrl();

            Picasso.with(context).load(imageURl).into(gameImage);
        }

        List<SimilarGame> gamesList = game.getSimilarGames();
        int [] count = {0};
        getSimilarGames(gamesList,count);

        count = new int[1];
        count[0]= 0;
        List<Video> videoList = game.getVideos();
        getYouTubeVideos(videoList,count);


    }

    private void getSimilarGames(final List<SimilarGame> gameList, final int count[] ) {

        if (count[0] == gameList.size() || count[0] == 10) {
            SimilarGamesAdapter similarGamesAdapter = new SimilarGamesAdapter(context,getSimilarGamesList());
            horizonatalListView.setAdapter(similarGamesAdapter);
            return;
        } else {
            SimilarGame game = gameList.get(count[0]);
            int id = game.getId();

            final String APPID_PARAM = "api_key";
            final String FORMAT_PARAM = "format";
            final String FIELD_LIST_PARAM = "field_list";
            String getGamesUrl = "http://www.giantbomb.com/api/game/" + id + "/";

            Uri buildUri = Uri.parse(getGamesUrl)
                    .buildUpon()
                    .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                    .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                    .appendQueryParameter(FIELD_LIST_PARAM, "id,name,image")
                    .build();

            StringRequest getSimilarGamesRequest = new StringRequest(Request.Method.GET, buildUri.toString(),

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();
                            DetailedGame game = gson.fromJson(response, DetailedGame.class);

                            SingleGame singleGame = game.getSingleGame();

                            getSimilarGamesList().add(singleGame);

                            count[0] = count[0]+1;
                            getSimilarGames(gameList, count);

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.toString());
                        }
                    }
            );


            RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(getSimilarGamesRequest);
        }

    }

    private void getYouTubeVideos(final List<Video> videoList , final int [] count){
        if(count[0] == videoList.size()){
            VideosAdapter videosAdapter = new VideosAdapter(context,getYoutubeVideoList());
            listView_videos.setAdapter(videosAdapter);
              setListViewHeightBasedOnChildren(listView_videos);
            return;
        }else{

            int videoID = videoList.get(count[0]).getId();

            final String APPID_PARAM = "api_key";
            final String FORMAT_PARAM = "format";
            final String FIELD_LIST_PARAM = "field_list";
            String getGamesUrl = "http://www.giantbomb.com/api/video/" + videoID + "/";

            Uri buildUri = Uri.parse(getGamesUrl)
                    .buildUpon()
                    .appendQueryParameter(APPID_PARAM, getString(R.string.api_key))
                    .appendQueryParameter(FORMAT_PARAM, getString(R.string.response_format))
                    .appendQueryParameter(FIELD_LIST_PARAM, "youtube_id")
                    .build();

            StringRequest getYouTubeVideosIDRequest = new StringRequest(Request.Method.GET, buildUri.toString(),

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();
                            try {
                                VideoRespnse videoResponse = gson.fromJson(response, VideoRespnse.class);

                                YouTubeVideo youTubeVideo = videoResponse.getYouTubeVideo();

                                getYoutubeVideoList().add(youTubeVideo.getYoutubeId());

                                count[0] = count[0] + 1;
                                getYouTubeVideos(videoList, count);
                            }catch (JsonSyntaxException e){
                                count[0] = count[0] + 1;
                                getYouTubeVideos(videoList, count);
                            }
                            catch (IllegalStateException e){
                                count[0] = count[0] + 1;
                                getYouTubeVideos(videoList, count);
                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", error.toString());
                        }
                    }
            );


            RequestQueueSingelton.getmInstance(getActivity().getApplicationContext()).addToRequestQueue(getYouTubeVideosIDRequest);


        }

    }

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
