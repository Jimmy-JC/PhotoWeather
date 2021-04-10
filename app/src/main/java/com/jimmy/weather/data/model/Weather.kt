package com.jimmy.weather.data.model

import com.google.gson.annotations.SerializedName

data class Weather(
    val name: String,
    val main: Temperature,
    @SerializedName("weather")
    val conditions: List<Condition>
)
