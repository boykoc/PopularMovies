package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class Main2ActivityFragment extends Fragment {

    public Main2ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        Intent intent = getActivity().getIntent();

            Bundle movie = intent.getExtras();
            Movie m = movie.getParcelable("com.package.Movie");

            ((TextView) rootView.findViewById(R.id.detail_textView)).setText(m.toString());



        return rootView;
    }
}
