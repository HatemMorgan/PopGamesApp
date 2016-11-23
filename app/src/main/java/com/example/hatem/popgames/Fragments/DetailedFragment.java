package com.example.hatem.popgames.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hatem.popgames.Activities.DetailedActivity;
import com.example.hatem.popgames.Activities.GallaryActivity;
import com.example.hatem.popgames.Activities.ReviewsActivity;
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
    private TextView textView_noVideos;

    private ArrayList<SingleGame> similarGamesList;
    private ArrayList<String> youtubeVideoList;
    private Context context;


    // detailed game data global variables

    private String gameName ;
    private String gameReleaseYear;
    private String gameImageURl;
    private int gameID;


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
        textView_noVideos = (TextView) rootView.findViewById(R.id.textView_NoVideos);

        // set an onItemClickListner to the horizontal list that contains the similar games
        // that fire an implicit intent that navigates the user to the detailed activity of the clicked game
        horizonatalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView_simalrGameImage = (ImageView) view.findViewById(R.id.imageView_icon);
                String gameID = imageView_simalrGameImage.getContentDescription().toString();

                // creating a bundle and add to it the gameID
                Bundle bundle = new Bundle();
                bundle.putInt("game_id",Integer.parseInt(gameID));
                // creating an implicit intent and add to it the created bundle
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // add an onItemClickListener to listView containing the videos
        // that will fire an explicit intent to play the video
        listView_videos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView_play = (ImageView) view.findViewById(R.id.imageView_play);
                // get the video id from the content description of the list item image as I stored when inflating results from videosData arraylist
                // into videos adapter
                String video_key =  imageView_play.getContentDescription().toString();

                // crate two intents one for web if the device running the application does not have an application that can run the video
                // the other one is used to play the youtube in any of the available apps on the user's device that could play the video
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video_key));
                try {
                    // if there are no apps in the user's device that can play the youtube video an ActivityNotFoundException will be thrown
                    // if ActivityNotFoundException was thrown , I will catch it and sent the web intent that plays the video in any on of the browsers
                    // available on the device
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });


        // add an onclick listener to Gallary button to navigate to gallary actitvity
        btn_Gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent = new Intent(getActivity(), GallaryActivity.class);
                Bundle bundle = getActivity().getIntent().getExtras();
                gallaryIntent.putExtras(bundle);
                startActivity(gallaryIntent);
                getActivity().finish();
            }
        });


        // add onclick listener to Review button to navigate to the game's review page

        btn_Reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("gameID",gameID);
                bundle.putString("gameName",gameName);
                bundle.putString("gameReleaseYear",gameReleaseYear);
                bundle.putString("gameImageUrl",gameImageURl);

                Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();

            }
        });

        return rootView ;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        int game_id = bundle.getInt("game_id");

        // setting gameID global variable to game_id passed by the bundle
        gameID = game_id ;

//        new Toast(context).makeText(context,game_id+"",Toast.LENGTH_LONG).show();
        getData(game_id+"");


        // todo : savaInstanceState here


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
                .appendQueryParameter(FIELD_LIST_PARAM, "id,name,deck,original_release_date,image,progress_image,reviews,,similar_games,videos")
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

        // setting game global variables
        gameName = game.getName();
        gameImageURl = game.getImage().getSmallUrl();
        gameReleaseYear = year;

        // getting similar games
        List<SimilarGame> gamesList = game.getSimilarGames();
        int [] count = {0};
        getSimilarGames(gamesList,count);


        // getting videos of the game
        count = new int[1];
        count[0]= 0;
        List<Video> videoList = game.getVideos();

        // check if videos list of the game is empty to tell the user that their is no videos for this game
        if (videoList == null ||videoList.size()== 0  ){
            textView_noVideos.setText("Sorry , No Videos available");
        }

        getYouTubeVideos(videoList,count);


    }
    //getSimilarGames at most 10 games from the similar games list of a specified game
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
                    .appendQueryParameter(FIELD_LIST_PARAM, "id,image")
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
    // load at most 6 videos from the videos list of the specified game
    private void getYouTubeVideos(final List<Video> videoList , final int [] count){
        if(count[0] == videoList.size() || count[0]==6){
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
