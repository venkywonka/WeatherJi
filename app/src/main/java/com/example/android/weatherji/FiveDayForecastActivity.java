package com.example.android.weatherji;

import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FiveDayForecastActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String>{
    String str_url5;
    String cityName;
    ArrayList<WeatherReport> weatherReportList;
    WeatherAdapter weatherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_day_forecast);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("bleh");
        str_url5 = "http://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&appid=3a179ea1f48f81fda35d9456e13129b7";
        Log.i("FiveDayForecastActivity","the created url: "+str_url5);
        Log.i("OnCreate() method","the getLoaderManager().initLoader(0,null,this) is gonna be executed bitch!");
        weatherReportList = new ArrayList<>();
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader() method","the onCreateLoader is executed bitch!");
        return new WeatherLoader(FiveDayForecastActivity.this,str_url5);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String json_response) {



        if(json_response==null){
            Log.i("onLoadFinished() method","no json data data");
            Toast.makeText(this,"Enter valid city name",Toast.LENGTH_SHORT).show();
            returnIntent();
        }
        else{
            Log.i("onLoadFinished() method","valid json response");
            parse(json_response);}
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        Log.i("onLoaderReset() method","the loader is reset ");
        weatherAdapter.clear();


    }

    private void parse(String json_response){

        try {
            JSONObject baseJsonResponse = new JSONObject(json_response);
            JSONArray baseArray = baseJsonResponse.getJSONArray("list");
            for(int i=0;i<baseArray.length();i+=8){
                JSONObject weatherObject = baseArray.getJSONObject(i);
                Long timeStamp = weatherObject.getLong("dt");
                JSONArray weatherList = weatherObject.getJSONArray("weather");
                JSONObject weather = weatherList.getJSONObject(0);
                String description = weather.getString("description");
                String icon = weather.getString("icon");
                weatherReportList.add(new WeatherReport("",description,icon,timeStamp,0.0,0.0,0.0));
            }

            setUI();
        } catch (JSONException e) {
            Log.e("JSON exception","error parsing the json response",e);
            e.printStackTrace();
        }
    }
    private void returnIntent(){
        Log.i("FIveDay.returnIntent()","return to main activity");
        Intent returnIntent = new Intent(FiveDayForecastActivity.this, MainActivity.class);
        // Start the new activity
        startActivity(returnIntent);
    }

    void setUI(){
        Log.i("five day forecast","UI set ");
        TextView t = (TextView)findViewById(R.id.city_heading);
        t.setText(cityName);
        ListView l = (ListView) findViewById(R.id.list);
        weatherAdapter = new WeatherAdapter(this,weatherReportList);
        l.setAdapter(weatherAdapter);



    }
}
