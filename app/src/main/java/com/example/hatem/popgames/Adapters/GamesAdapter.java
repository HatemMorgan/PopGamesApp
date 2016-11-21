package com.example.hatem.popgames.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.popgames.ORM.Games;
import com.example.hatem.popgames.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hatem on 11/2/16.
 */
public class GamesAdapter extends BaseAdapter {

    private ArrayList<Games> gamesList ;
    private Context context ;
    private LayoutInflater layoutInflater ;

    public GamesAdapter(Context context , ArrayList<Games> gamesList){
        this.context = context ;
        this.gamesList = gamesList ;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        private ImageView gameImage;
        private TextView gameName;
    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Games getItem(int position) {
        return  gamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_view_list_item,null);
            viewHolder = new ViewHolder() ;
            viewHolder.gameImage = (ImageView) convertView.findViewById(R.id.imageView_icon);
            viewHolder.gameName = (TextView) convertView.findViewById(R.id.txt_view_icon_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Games game = getItem(position);

        viewHolder.gameName.setText(game.getName());

        if(game.getImage() != null) {
            String imageURl = game.getImage().getScreenUrl();

            Picasso.with(context).load(imageURl).placeholder(R.drawable.progress_animation).into(viewHolder.gameImage);
        }
        return convertView ;

    }


}
