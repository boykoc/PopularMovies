package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by boykoco on 09/16/2015.
 */
public class ImageAdapter<S> extends ArrayAdapter {
    private Context context;
    private int resource;
    private int textViewResourceId;
    private List objects;
    public ImageAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: remove LOG_TAG as needed
        final String LOG_TAG = ImageAdapter.class.getSimpleName();
        final String TMDB_IMAGE_URL_BASE = "http://image.tmdb.org/t/p/";
        final String TMDB_IMAGE_SIZE = "w185";
        ImageView imageView;

        if(convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Movie movie = (Movie) objects.get(position);

        // TODO: remove Logs as needed
        Log.w("myApp", TMDB_IMAGE_URL_BASE + TMDB_IMAGE_SIZE + movie.getPosterPath());

        // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Picasso.with(context).load(TMDB_IMAGE_URL_BASE + TMDB_IMAGE_SIZE + movie.getPosterPath())
                .into(imageView);
        return imageView;
        //return super.getView(position, convertView, parent);
    }
}



