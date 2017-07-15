package com.example.android.weatherji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ed;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    void openCurrentWeatherReport(View view){

       try{ ed = (EditText) findViewById(R.id.city);
        if(ed==null){ Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();}

        else{

            cityName = ed.getText().toString();
            ed.setText("");
            cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1).toLowerCase();
            Log.i("GO button","the modified City: "+cityName);
            Intent cityIntent = new Intent(MainActivity.this, CurrentWeatherActivity.class);
            cityIntent.putExtra("blah",cityName);

            // Start the new activity
            startActivity(cityIntent);}}catch (IllegalStateException e){ Toast.makeText(this,"ENTER SOMETHING!",Toast.LENGTH_SHORT).show();}
    }
}
