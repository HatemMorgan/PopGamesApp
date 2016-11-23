package com.example.hatem.popgames.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.hatem.popgames.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hatem on 11/23/16.
 */
public class ImagesAdapter extends BaseAdapter {
    private Context context ;
    private ArrayList<String> imagesListThumbURl ;
    private ArrayList<String> imagesListMediumURl ;
    private int itemBackground;



    public ImagesAdapter(Context context ,ArrayList<String> imagesListThumbURl,ArrayList<String>imagesListMediumURl){
        this.context = context ;
        this.imagesListThumbURl = imagesListThumbURl;
        this.imagesListMediumURl = imagesListMediumURl;

        // sets a grey background; wraps around the images
        TypedArray a =context.obtainStyledAttributes(R.styleable.MyGallery);
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();

    }

    @Override
    public int getCount() {
        return imagesListThumbURl.size();
    }

    @Override
    public String getItem(int position) {
        return imagesListThumbURl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(imagesListThumbURl.get(position)).placeholder(R.drawable.progress_animation).into(imageView);
        imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
        imageView.setBackgroundResource(itemBackground);
        imageView.setContentDescription(imagesListMediumURl.get(position));
        return imageView;
    }
}
