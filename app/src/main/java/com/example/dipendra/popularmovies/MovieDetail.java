package com.example.dipendra.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dipendra.popularmovies.models.Movies;
import com.squareup.picasso.Picasso;


public class MovieDetail extends AppCompatActivity {
    private Movies m;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie", m);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        if (savedInstanceState != null) {
            m = savedInstanceState.getParcelable("movie");
        } else {
            m = getIntent().getExtras().getParcelable("movie");
        }
        ImageView imageView = (ImageView) findViewById(R.id.detailposter);
        String picurl = new String("http://image.tmdb.org/t/p/w342/");
        picurl = picurl + m.getPoster_path();
        Picasso.with(this).load(picurl).placeholder(R.drawable.progress_animation).error(R.drawable.ic_error).into(imageView);
        TextView title = (TextView) findViewById(R.id.detailtitle);
        TextView des = (TextView) findViewById(R.id.detaildescription);
        TextView reldate = (TextView) findViewById(R.id.detailreleasedate);
        TextView vote = (TextView) findViewById(R.id.detailvoteaverage);
        title.setText(m.getTitle());
        des.setText(m.getOverview());
        reldate.setText(m.getRelease_date());
        vote.setText(m.getVote_average());
    }

}
