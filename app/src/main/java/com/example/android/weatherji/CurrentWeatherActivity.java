package com.example.android.weatherji;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentWeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    WeatherReport weatherReport;
    String cityName;
    String str_url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        Intent intent = getIntent();
        cityName = intent.getStringExtra("blah");
        str_url = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=3a179ea1f48f81fda35d9456e13129b7";
        Log.i("CurrentWeatherActivity","the created url: "+str_url);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected==false){
            Toast.makeText(this,"check internet connection",Toast.LENGTH_SHORT);
            returnIntent();
        }
        else{

            Log.i("OnCreate() method","the getLoaderManager().initLoader(0,null,this) is gonna be executed bitch!");
            getLoaderManager().initLoader(0,null,this);}


    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.i("onCreateLoader() method","the onCreateLoader is executed bitch!");
        return new WeatherLoader(CurrentWeatherActivity.this,str_url);
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


    }


private void parse(String json_response){

        try {
            JSONObject baseJsonResponse = new JSONObject(json_response);
            String cityName = baseJsonResponse.getString("name");
            Long timeStamp = baseJsonResponse.getLong("dt");
            Log.i("json parsing","the timestamp: "+timeStamp.toString());
            JSONArray weatherArray = baseJsonResponse.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);

            String description = weather.getString("description");
            String icon = weather.getString("icon");
            JSONObject main = baseJsonResponse.getJSONObject("main");
            Double temp = main.getDouble("temp");
            Double pressure = main.getDouble("pressure");
            Double humidity = main.getDouble("humidity");
            weatherReport = new WeatherReport(cityName,description,icon,timeStamp,temp,pressure,humidity);
            setUI();


        } catch (JSONException e) {
            Log.e("JSON exception","error parsing the json response",e);
            e.printStackTrace();
        }
    }




        public void setUI(){
            Log.i("setUI","The setUI() for displaying current weather data is called");
            ProgressBar loading = (ProgressBar)findViewById(R.id.loading) ;
            loading.setVisibility(View.INVISIBLE);
            TextView t = (TextView) findViewById(R.id.city_name);
            t.setText(weatherReport.getName());
            ImageView i =(ImageView)findViewById(R.id.weather_icon);
            Picasso.with(this).load("http://openweathermap.org/img/w/"+weatherReport.getIcon()+".png").into(i);
            Log.i("setUI,current","the timestamp: "+weatherReport.getTimeStamp().toString());
            t=(TextView)findViewById(R.id.date);
            t.setText(new SimpleDateFormat("dd MMM, yyyy").format(new Date(weatherReport.getTimeStamp() * 1000L)));
            t = (TextView)findViewById(R.id.temperature);
            Double temp = weatherReport.getTemperature() - 273.16;
            DecimalFormat format = new DecimalFormat("0.0");
            t.setText("temperature  :  "+format.format(temp)+" degrees");
            t = (TextView)findViewById(R.id.pressure);
            t.setText("pressure  :  "+weatherReport.getPressure()+" hPa");
            t = (TextView)findViewById(R.id.humidity);
            t.setText("humidity  :  "+weatherReport.getHumidity()+" %");
            t=(TextView)findViewById(R.id.description);
            t.setText("\""+weatherReport.getDescription()+"\"");}


    private void returnIntent(){
        Log.i("returnIntent()","return to main activity");
        Intent returnIntent = new Intent(CurrentWeatherActivity.this, MainActivity.class);
        // Start the new activity
        startActivity(returnIntent);
    }
    void fiveDayForecast(View v){
        Intent i = new Intent(CurrentWeatherActivity.this,FiveDayForecastActivity.class);
        i.putExtra("bleh",cityName);
        startActivity(i);
    }
    }




