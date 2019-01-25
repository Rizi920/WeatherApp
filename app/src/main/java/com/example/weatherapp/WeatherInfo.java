package com.example.weatherapp;

public class WeatherInfo {
    public String description;
    public String minTemp;
    public String maxTemp;

    public WeatherInfo(String desc, String min, String max){

        this.description=desc;
        this.maxTemp=max;
        this.minTemp=min;
    }
}
