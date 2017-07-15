package com.example.android.weatherji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 15-07-2017.
 */

public class WeatherAdapter extends ArrayAdapter<WeatherReport> {
    public WeatherAdapter(Context context, List<WeatherReport> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.forecast_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        WeatherReport currentWeatherReport = getItem(position);

        // Find the TextView with view ID magnitude
        TextView t = (TextView) listItemView.findViewById(R.id.date_5);
        t.setText(new SimpleDateFormat("dd MMM, yyyy").format(new Date(currentWeatherReport.getTimeStamp() * 1000L)));
        t=(TextView) listItemView.findViewById(R.id.description_5);
        t.setText(currentWeatherReport.getDescription());
        ImageView i = (ImageView) listItemView.findViewById(R.id.icon_5);
        Picasso.with(this.getContext()).load("http://openweathermap.org/img/w/"+currentWeatherReport.getIcon()+".png").into(i);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */



}
