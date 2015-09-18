package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by boykoco on 09/17/2015.
 *
 * Create custom class that implements Parcelable to be able to pass movie data to the detail
 * activity.
 */
public class Movie implements Parcelable {
    // For Parcelable documentation:
    // http://developer.android.com/reference/android/os/Parcelable.html
    // Referenced Udacity webcast and http://shri.blog.kraya.co.uk/2010/04/26/android-parcel-data-to-pass-between-activities-using-parcelable-classes/


    // TODO: change datatypes as needed
    private String id;
    private String poster_path;
    private String original_title;
    private String overview;
    private String vote_average;
    private String release_date;

    public Movie(String id, String poster_path, String original_title, String overview,
                 String vote_average, String release_date) {
        this.id = id;
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(release_date);
    }


    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}
