package com.jimmy.weather.data.network

import com.jimmy.weather.BuildConfig
import com.jimmy.weather.data.model.Location
import com.jimmy.weather.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String = "metric",
        @Query("appid") key: String = BuildConfig.API_KEY
    ): Weather

    @GET("http://ip-api.com/json")
    suspend fun getLocation(): Location

}