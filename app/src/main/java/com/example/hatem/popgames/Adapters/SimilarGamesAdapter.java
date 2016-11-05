package com.example.hatem.popgames.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.popgames.ORM.SingleGame;
import com.example.hatem.popgames.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hatem on 11/4/16.
 */
public class SimilarGamesAdapter extends BaseAdapter {

    private Context context ;
    private ArrayList<SingleGame> similarGameArrayList ;
    private LayoutInflater layoutInflater ;

    public SimilarGamesAdapter(Context context , ArrayList<SingleGame> similarGameArrayList){
        this.context = context ;
        this.similarGameArrayList = similarGameArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        private ImageView gameImage;
        private TextView gameName;
    }

    @Override
    public int getCount() {
        return similarGameArrayList.size();
    }

    @Override
    public SingleGame getItem(int position) {
        return similarGameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.similar_games_gallary_item,null);
            viewHolder = new ViewHolder() ;
            viewHolder.gameImage = (ImageView) convertView.findViewById(R.id.imageView_icon);
            viewHolder.gameName = (TextView) convertView.findViewById(R.id.txt_view_icon_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SingleGame game = getItem(position);

        viewHolder.gameName.setText(game.getName());

        if(game.getImage() != null) {
            String imageURl = game.getImage().getSmallUrl();

            Picasso.with(context).load(imageURl).into(viewHolder.gameImage);
        }
        return convertView ;

    }
}
