package com.example.android.weatherji;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by Admin on 14-07-2017.
 */

public class WeatherLoader extends AsyncTaskLoader<String> {
    private String str_url;

    public WeatherLoader(Context context,String str_url) {
        super(context);
        this.str_url = str_url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        Log.i("loadInBackground()","a new thread is formed bitch!");
        String json_response = Utils.getJSONResponseFromURL(str_url);
        return json_response;
    }

}
