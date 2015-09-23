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
    private String posterPath;
    private String originalTitle;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public Movie(String id, String poster_path, String original_title, String overview,
                 String vote_average, String release_date) {
        this.id = id;
        this.posterPath = poster_path;
        this.originalTitle = original_title;
        this.overview = overview;
        this.voteAverage = vote_average;
        this.releaseDate = release_date;
    }

    protected Movie(Parcel in) {
        id = in.readString();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
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
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
