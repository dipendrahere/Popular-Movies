package com.example.dipendra.popularmovies;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dipendra.popularmovies.models.Movies;
import com.example.dipendra.popularmovies.models.ParcelData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    final private int SETREQ = 987;
    private ArrayList<Movies> movieList;
    private String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.movieList = new ArrayList<Movies>();
        this.mode = getmode();
        if(savedInstanceState != null) {
            ParcelData p = savedInstanceState.getParcelable("a");
            this.movieList = p.getMoviesArrayList();
            GridView gr = (GridView) findViewById(R.id.gridmovie);
            MovieAdapter m = new MovieAdapter(this, movieList);
            gr.setAdapter(m);
        }
        else {
            refresh(mode);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ParcelData p = new ParcelData();
        p.setMoviesArrayList(movieList);
        outState.putParcelable("a", p);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            refresh(mode);
        }
        else if(id == R.id.action_settings){
            Intent i = new Intent(MainActivity.this, Settings.class);
            startActivityForResult(i, SETREQ);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETREQ){
            String temp = getmode();
            if(temp.equalsIgnoreCase(mode) == false){
                mode = temp;
                refresh(mode);
            }
        }
    }

    private void refresh(String mode) {
        URL u = null;
        String url = new String("http://api.themoviedb.org/3/movie/");
        if(mode.equalsIgnoreCase("popular")){
            url = url+"popular?api_key="+getString(R.string.apikey);
        }
        else if(mode.equalsIgnoreCase("rating")){
            url = url+"top_rated?api_key="+getString(R.string.apikey);
        }
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        MyTask task = new MyTask(MainActivity.this);
        try {
            movieList = task.execute(u).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private String getmode(){
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(this);
        return s.getString("choice", "Rating");
    }

}

