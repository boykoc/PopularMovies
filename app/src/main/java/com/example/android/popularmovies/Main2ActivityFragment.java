package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class Main2ActivityFragment extends Fragment {

    public Main2ActivityFragment() {
    }
// TODO: enable back button.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        Intent intent = getActivity().getIntent();

        Bundle movie = intent.getExtras();
        Movie m = movie.getParcelable("com.package.Movie");
        final String TMDB_IMAGE_URL_BASE = "http://image.tmdb.org/t/p/";
        final String TMDB_IMAGE_SIZE = "w185";

        ((TextView) rootView.findViewById(R.id.title_textView)).setText(m.getOriginalTitle());
        ((TextView) rootView.findViewById(R.id.year_textView)).setText(m.getReleaseDate());
        ((TextView) rootView.findViewById(R.id.rating_textView)).setText(m.getVoteAverage() + "/10");
        ((TextView) rootView.findViewById(R.id.detail_textView)).setText(m.getOverview());

        Picasso.with(getActivity()).load(TMDB_IMAGE_URL_BASE + TMDB_IMAGE_SIZE + m.getPosterPath())
                .into((ImageView) rootView.findViewById(R.id.movie_poster_imageView));


        return rootView;
    }
}
