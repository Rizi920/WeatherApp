package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//call (interface) named WeatherService for retrofit, with getCurrentWeatherData method to invoke
interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("APPID") app_id: String): Call<WeatherData>
}
