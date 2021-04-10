package com.jimmy.weather.data.repository

import com.jimmy.weather.base.BaseRepository
import com.jimmy.weather.data.model.Location
import com.jimmy.weather.data.network.WeatherAPI
import com.jimmy.weather.data.persistence.ImageDao
import com.jimmy.weather.data.persistence.ImageEntity

class WeatherDetailRepository(
    private val api: WeatherAPI,
    private val imageDao: ImageDao
) : BaseRepository() {

    suspend fun getLocation() = handleResponse { api.getLocation() }

    suspend fun getWeather(location: Location) =
        handleResponse { api.getWeather(location.lat, location.lon) }

    suspend fun insertImage(image: ImageEntity) {
        imageDao.insertImage(image)
    }
}