package com.example.android.weatherji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        sharedPreferences = this.getSharedPreferences("com.example.android.weatherji",MODE_PRIVATE);
        ed = (EditText) findViewById(R.id.city);
        ed.setText(sharedPreferences.getString("city",""));


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

            // Start the new activity
            startActivity(cityIntent);}}catch (IllegalStateException e){ Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();}
    }
}
