package com.example.weatherapp

import com.google.gson.annotations.SerializedName


//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Coord {
    @SerializedName("lon")
    var lon: Float = 0.toFloat()
    @SerializedName("lat")
    var lat: Float = 0.toFloat()
}
