package com.example.weatherapp

import com.google.gson.annotations.SerializedName

//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Clouds {

    @SerializedName("all")
    var all: Float = 0.toFloat()
}
