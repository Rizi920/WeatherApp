package com.example.weatherapp

import com.google.gson.annotations.SerializedName


//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Sys {

    @SerializedName("country")
    var country: String = ""
    @SerializedName("sunrise")
    var sunrise: Long = 0
    @SerializedName("sunset")
    var sunset: Long = 0
    @SerializedName("message")
    var message: String = ""
}
