package com.example.weatherapp

import com.google.gson.annotations.SerializedName


//Data model for deserialization of received json data, used inside the Main data model (WeatherData)
class Weather {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("main")
    var main: String = ""
    @SerializedName("description")
    var description: String = ""
    @SerializedName("icon")
    var icon: String = ""
}
