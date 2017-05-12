package com.example.dipendra.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dipendra on 20/01/17.
 */

public class Movies implements Parcelable {
    private String poster_path;
    private String title;
    private String overview;
    private String release_date;
    private String vote_average;

    public Movies() {

    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return this.vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    protected Movies(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote_average);
    }
}
