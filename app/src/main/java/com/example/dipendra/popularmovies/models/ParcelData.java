package com.example.dipendra.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dipendra on 21/01/17.
 */

public class ParcelData implements Parcelable {
    private ArrayList<Movies> moviesArrayList;

    public ParcelData() {
        this.moviesArrayList = new ArrayList<Movies>();
    }

    public ArrayList<Movies> getMoviesArrayList() {
        return this.moviesArrayList;
    }

    public void setMoviesArrayList(ArrayList<Movies> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
    }

    protected ParcelData(Parcel in) {

    }

    public static final Creator<ParcelData> CREATOR = new Creator<ParcelData>() {
        @Override
        public ParcelData createFromParcel(Parcel in) {
            return new ParcelData(in);
        }

        @Override
        public ParcelData[] newArray(int size) {
            return new ParcelData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(moviesArrayList);

    }
}
