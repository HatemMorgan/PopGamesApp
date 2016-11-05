package com.example.hatem.popgames.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.popgames.R;

import java.util.ArrayList;

/**
 * Created by hatem on 11/4/16.
 */
public class VideosAdapter extends BaseAdapter {

    private ArrayList<String> videoStringArrayList ;
    private Context context ;
    private LayoutInflater layoutInflater ;

    public VideosAdapter(Context context , ArrayList<String> videoStringArrayList){
        this.context = context ;
        this.videoStringArrayList = videoStringArrayList ;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        private TextView videoID;
        private ImageView playImage;
    }

    @Override
    public int getCount() {
        return videoStringArrayList.size();
    }

    @Override
    public String getItem(int position) {
        return videoStringArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;


        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.videos_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.videoID = (TextView) convertView.findViewById(R.id.textView_video_id);
            viewHolder.playImage = (ImageView) convertView.findViewById(R.id.imageView_play);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String text = "Youtube Video";
        String youtubeVideoID =getItem(position);
        int n = position ;
        n++;
        viewHolder.videoID.setText(text+" "+(n));
        viewHolder.playImage.setContentDescription(youtubeVideoID);

        return convertView;
    }
}
