package com.example.weatherapp

import com.google.gson.annotations.SerializedName

//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Wind {
    @SerializedName("speed")
    var speed: Float = 0.toFloat()
    @SerializedName("deg")
    var deg: Float = 0.toFloat()
}
