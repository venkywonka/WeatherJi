package com.example.android.weatherji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed;
    String cityName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentWeatherActivity.cityName=null;
        sharedPreferences = this.getSharedPreferences("com.example.android.weatherji",MODE_PRIVATE);
        ed = (EditText) findViewById(R.id.city);
        ed.setText(sharedPreferences.getString("city",""));


    }

    void mapsIntent(View view){

        Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapsIntent);

    }


    void openCurrentWeatherReport(View view){

       try{
        if(ed==null){ Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();}

        else{

            cityName = ed.getText().toString();
            sharedPreferences.edit().putString("city",cityName).apply();
            ed.setText("");
            cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1).toLowerCase();
            Log.i("GO button","the modified City: "+cityName);
            Intent cityIntent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
            cityIntent.putExtra("blah",cityName);

            CurrentWeatherActivity.flag = 0;

            // Start the new activity
            startActivity(cityIntent);}}catch (IllegalStateException e){ Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.history) {
            Intent i = new Intent(MainActivity.this,History.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
