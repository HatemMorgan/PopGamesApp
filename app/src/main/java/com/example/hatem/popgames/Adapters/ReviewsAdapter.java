package com.example.hatem.popgames.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatem.popgames.ORM.Review;
import com.example.hatem.popgames.R;

import java.util.List;

/**
 * Created by hatem on 11/23/16.
 */
public class ReviewsAdapter extends BaseAdapter {
    private List<Review> reviewList ;
    private Context context ;
    private LayoutInflater layoutInflater ;

    static class ViewHolder {
        private TextView reviewerName;
        private  TextView reviewContent;
        private ImageView reviewerImage;
        private TextView reviewRate;
    }

    public ReviewsAdapter(Context context , List<Review> reviewList){
        this.context = context ;
        this.reviewList = reviewList ;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Review getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.review_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.reviewContent = (TextView) convertView.findViewById(R.id.textView_Review_content);
            viewHolder.reviewerName = (TextView) convertView.findViewById(R.id.textView_Review_authorName);
            viewHolder.reviewerImage = (ImageView) convertView.findViewById(R.id.imageView_Review_AuthorImage);
            viewHolder.reviewRate = (TextView) convertView.findViewById(R.id.textView_reviewRate);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Review review = getItem(position);
        viewHolder.reviewerName.setText(review.getReviewer());
        viewHolder.reviewContent.setText(review.getDeck());
        viewHolder.reviewerImage.setContentDescription(review.getSiteDetailUrl());
        viewHolder.reviewRate.setText(review.getScore()+"/5");
        return convertView;
    }
}
