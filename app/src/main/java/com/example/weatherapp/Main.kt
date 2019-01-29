package com.example.weatherapp

import com.google.gson.annotations.SerializedName

//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Main {
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("temp_min")
    var temp_min: Float = 0.toFloat()
    @SerializedName("temp_max")
    var temp_max: Float = 0.toFloat()
    @SerializedName("sea_level")
    var sea_level: Float = 0.toFloat()
    @SerializedName("grnd_level")
    var grnd_level: Float = 0.toFloat()
}
