package com.example.dipendra.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.dipendra.popularmovies.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dipendra on 24/01/17.
 */

public class MovieAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Movies> arrayList;

    public MovieAdapter(Activity a, ArrayList<Movies> arrayList) {
        this.context = a;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder v;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.griditem, viewGroup, false);
            v = new ViewHolder();
            v.imageView = (ImageView) view.findViewById(R.id.imageViewmovieposter);
            view.setTag(v);
        } else {
            v = (ViewHolder) view.getTag();
        }
        String picurl = new String("http://image.tmdb.org/t/p/w342/");
        picurl = picurl + arrayList.get(i).getPoster_path();
        Picasso.with(context).load(picurl).placeholder(R.drawable.progress_animation).error(R.drawable.ic_error).into(v.imageView);
        final int y = i;
        v.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetail.class);
                i.putExtra("movie", arrayList.get(y));
                context.startActivity(i);
            }
        });


        return view;
    }

    static class ViewHolder {
        ImageView imageView;
    }

}
