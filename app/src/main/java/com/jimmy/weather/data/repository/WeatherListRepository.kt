package com.jimmy.weather.data.repository

import com.jimmy.weather.base.BaseRepository
import com.jimmy.weather.data.persistence.ImageDao

class WeatherListRepository(private val imageDao: ImageDao) : BaseRepository() {

    suspend fun getImages() = imageDao.getImages()
}