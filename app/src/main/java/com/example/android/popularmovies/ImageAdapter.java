package com.example.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *
 */

/**
 * ImageAdapter to handle creating the ImageView for each movie and returns it to the main activity.
 *
 * Created by boykoco on 09/16/2015.
 *
 * @param <S>
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

    /**
     * Generate the ImageView for each movie poster using picasso.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        Picasso.with(context).load(TMDB_IMAGE_URL_BASE + TMDB_IMAGE_SIZE + movie.getPosterPath())
                .into(imageView);
        return imageView;
    }
}



