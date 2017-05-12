package com.example.dipendra.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Created by dipendra on 23/01/17.
 */

public class Settings extends PreferenceActivity {
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Internet non-availability may lead to no change is filter!", Toast.LENGTH_LONG).show();
        }
    }
}
