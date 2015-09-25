package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Main fragment for app.
 */
public class MainActivityFragment extends Fragment {

    ImageAdapter mMovieAdapter;

    public MainActivityFragment() {
    }

    /**
     * Override to allow this fragment to handle menu events.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    /**
     * Handle option items being selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Override onCreateView to initialize adapter, fill gridview with movie data and
     * setOnClickItemListener.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new ImageAdapter(
                getActivity(),
                R.layout.list_item_movie,
                R.id.list_item_movie_imageView,
                new ArrayList<Movie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_poster_gridView);
        gridView.setAdapter(mMovieAdapter);


        // Set click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * OnItemClick launch an explicit intent for the detail activity for the selected item.
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity();
                Movie movie = (Movie) mMovieAdapter.getItem(position); // Get the selected movie.

                // Call an explicit intent to launch the detail activity.
                Intent detailActivity = new Intent(context, MovieDetailActivity.class);
                detailActivity.putExtra("com.package.Movie", movie);
                startActivity(detailActivity);
            }
        });

        return rootView;

    }

    /**
     * Override onStart to load movie data when the app starts.
     */
    @Override
    public void onStart() {
        super.onStart();
        getMovieData();
    }

    /**
     * Helper method to get Movie data
     */
    private void getMovieData() {
        // Create movie task and execute it
        FetchMovieDataTask movieDataTask = new FetchMovieDataTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_by = prefs.getString("sortOrder", "popularity.desc");
        movieDataTask.execute(sort_by);
    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, Movie[]> {
        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();

        /**
         * Re-format the date string to only include year of release. Currently the release date is
         * returned from the API as a string in YYYY-MM-DD format.
         *
         * @param apiDate
         * @return
         */
        private String formatDate(String apiDate) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy"); // Set the format.
            Date formatedDate = null; // Initialize Date date.
            try {
                formatedDate = format.parse(apiDate); // Create a date from the string.
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return format.format(formatedDate); // Create a formatted string from date.
        }

        /**
         * BORROWED DESCRIPTION FROM LESSON 1-3 OF DEVELOPING ANDROID APPS
         * Why: It's a great description and I'm going to be doing the same thing here
         *
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         *
         * @param movieDataJsonStr
         * @return
         * @throws JSONException
         */
        private Movie[] getMovieDataFromJson(String movieDataJsonStr) throws JSONException {
            // Names of the JSON objects that I need to get.
            final String TMDB_RESULTS = "results";
            final String TMDB_ID = "id";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_VOTE_AVERAGE = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";

            JSONObject movieDataJson = new JSONObject(movieDataJsonStr);
            JSONArray movieDataArray = movieDataJson.getJSONArray(TMDB_RESULTS);

            Movie[] movieResults = new Movie[movieDataArray.length()];

            // Loop through all movie results to get necessary data.
            for(int i=0; i < movieDataArray.length(); i++) {
                // Strings for each movie result item.
                String id;
                String poster_path;
                String original_title;
                String overview;
                String vote_average;
                String release_date;

                // Get the current movie result.
                JSONObject movieData = movieDataArray.getJSONObject(i);
                id = movieData.getString(TMDB_ID);
                poster_path = movieData.getString(TMDB_POSTER_PATH);
                original_title = movieData.getString(TMDB_ORIGINAL_TITLE);
                overview = movieData.getString(TMDB_OVERVIEW);
                vote_average = movieData.getString(TMDB_VOTE_AVERAGE);
                release_date = formatDate(movieData.getString(TMDB_RELEASE_DATE));
                // Add a new movie object to the Array.
                movieResults[i] = new Movie(id, poster_path, original_title, overview, vote_average,
                        release_date);
            }
            return movieResults;
        }

        /**
         * Much of this method is adapted from the Sunshine app course materials.
         *
         * @param params
         * @return
         */
        @Override
        protected Movie[] doInBackground(String... params) {
            // Verify there is a param, if not stop process.
            if (params.length == 0) {
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieDataJsonStr = null;
            String api_key = "YOUR_API_KEY_HERE";

            try {
                // Construct the URL for the TMDB query
                // After reviewing the TMDB documentation for their API I've decided to
                // stick with using the discovery end-point for project 1.
                // I've found that there are many ways to get the highest rated movies (e.g.
                // sort_by=vote_average.desc only or top_rated end-point). To keep it consistent
                // and to help ensure newer movies, with a high number of votes are included I have
                // combined various params.  I have compared the returned JSON strings from
                // top_rated and what I am using and feel the results are consistent while using
                // the same end-point.

                final String FORECAST_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String QUERY_PARAM = "sort_by";
                final String API_KEY = "api_key";

                // Get string for pref_sort_order_highest_rated.
                String sort_by = getResources().getString(R.string.pref_sort_order_highest_rated);

                Uri.Builder builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon();
                builtUri.appendQueryParameter(QUERY_PARAM, params[0]);
                // I want to append more query params to the URI builder if the param is requesting
                // the movies to be sorted by highest rated.
                if ( params[0].equals(sort_by) ) {
                    builtUri.appendQueryParameter("vote_average.gte", "8.0")
                            .appendQueryParameter("vote_count.gte", "50");
                }

                builtUri.appendQueryParameter(API_KEY, api_key).build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieDataJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieDataJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for(Movie dayForecastStr : result) {
                    mMovieAdapter.add(dayForecastStr);
                }
                // New data is back from the server.
            }
        }
    }
}