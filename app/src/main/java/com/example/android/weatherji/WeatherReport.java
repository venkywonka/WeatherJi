package com.example.android.weatherji;

/**
 * Created by Admin on 13-07-2017.
 */

public class WeatherReport {
    private String name;
    private String description;
    private String icon;
    private Long timeStamp;
    private Double temperature;
    private Double pressure;
    private Double humidity;

    WeatherReport(String name, String description,String icon,Long timeStamp,Double temperature,Double pressure,Double humidity){
        this.name = name;
        this.description = description;
        this.icon= icon;
        this.timeStamp=timeStamp;
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getIcon(){return  icon;}
    public Long getTimeStamp(){return timeStamp;}
    public Double getTemperature(){return temperature;}
    public Double getPressure(){return pressure;}
    public Double getHumidity(){return humidity;}



}
