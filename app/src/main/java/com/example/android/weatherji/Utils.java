package com.example.android.weatherji;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Admin on 14-07-2017.
 */

public class Utils {
    private Utils(){}

    public static String getJSONResponseFromURL(String str_url){
        URL url = createUrl(str_url);
        String jsonResponse = "";

        if (url == null) {
            Log.e("QueryUtils","url is null hence json response is null");
            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(60000 /* milliseconds */);
            urlConnection.setConnectTimeout(60000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                Log.i("Query.utils","response code: "+ urlConnection.getResponseCode() );
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i("Query.utils","the json response string : "+ jsonResponse);
            } else {
                Log.e("", "Problem is with the url connection. Error response code: " + urlConnection.getResponseCode());
                return null;
            }
        } catch (IOException e) {
            Log.e("Query.Utils", "Problem retrieving the earthquake JSON results.", e); 
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(jsonResponse=="")Log.e("","json response is empty");
        return jsonResponse;
    }

    private static URL createUrl(String str_url) {
        URL url = null;
        try {
            url = new URL(str_url);
        } catch (MalformedURLException e) {
            Log.e("createUrl()", "Error with creating URL ", e);
            return null;
        }
        return url;
    }

    private static String readFromStream(InputStream inputStream){
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }}catch (IOException e){
                e.printStackTrace();
                Log.e("readFromStream()","error reading input stream",e);
                return null;
            }
        }
        return output.toString();
    }

}

