package com.example.dipendra.popularmovies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dipendra.popularmovies.models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dipendra on 20/01/17.
 */

public class MyTask extends AsyncTask<URL, String, ArrayList<Movies>> {
    private Activity activity;
    private GridView gridView;
    private StringBuffer json;
    private ProgressDialog progressDialog;
    public MyTask(Activity activity){
        json = new StringBuffer("");
        this.activity = activity;
        gridView = (GridView) activity.findViewById(R.id.gridmovie);
        progressDialog = new ProgressDialog(activity);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage("Loading! Please wait!");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        if(!isNetworkAvailable()) {
            Toast.makeText(activity, "Internet connection is not there!", Toast.LENGTH_LONG).show();
            SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
            json = new StringBuffer(sharedPreferences.getString("saved",null));
        }

    }
    @Override
    protected ArrayList<Movies> doInBackground(URL... urls) {
        ArrayList<Movies> arrayList;
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader = null;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urls[0].openConnection();
            inputStreamReader = new InputStreamReader(urls[0].openStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                json.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayList = parse(json);
        return arrayList;
    }
    private ArrayList<Movies> parse(StringBuffer json){
        ArrayList<Movies> arrayList = new ArrayList<Movies>();
        JSONArray ar;
        JSONObject baseobj;
        try {
            baseobj = new JSONObject(json.toString());
            ar = baseobj.getJSONArray("results");
            for(int i = 0; i < ar.length(); i++){
                JSONObject movie = ar.getJSONObject(i);
                Movies m = new Movies();
                m.setPoster_path(movie.getString("poster_path"));
                m.setOverview(movie.getString("overview"));
                m.setRelease_date(movie.getString("release_date"));
                m.setTitle(movie.getString("title"));
                m.setVote_average(movie.getString("vote_average"));
                arrayList.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    @Override
    protected void onPostExecute(ArrayList<Movies> s) {
        super.onPostExecute(s);
        SharedPreferences.Editor ed = (SharedPreferences.Editor) activity.getPreferences(Context.MODE_PRIVATE).edit();
        ed.putString("saved", json.toString());
        ed.commit();
        MovieAdapter adapter = new MovieAdapter(activity, s);
        gridView.setAdapter(adapter);
        progressDialog.cancel();
    }
}
