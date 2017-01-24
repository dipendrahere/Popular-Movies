package com.example.dipendra.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dipendra.popularmovies.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dipendra on 24/01/17.
 */

public class MovieAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<Movies> arrayList;
    public MovieAdapter(Activity a, ArrayList<Movies> arrayList){
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
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.griditem, viewGroup, false);
            v = new ViewHolder();
            v.imageView = (ImageView) view.findViewById(R.id.imageViewmovieposter);
            view.setTag(v);
        }
        else {
            v = (ViewHolder) view.getTag();
        }
        String picurl = new String("http://image.tmdb.org/t/p/w342/");
        picurl = picurl + arrayList.get(i).getPoster_path();
        if(isNetworkAvailable())
            Picasso.with(context).load(picurl).into(v.imageView);
        else{

        }
        final int y =i;
            v.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, arrayList.get(y).getTitle(), Toast.LENGTH_LONG).show();
                }
            });


        return view;
    }
    static class ViewHolder{
        ImageView imageView;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
