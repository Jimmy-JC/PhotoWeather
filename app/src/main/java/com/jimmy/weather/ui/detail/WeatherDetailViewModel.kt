package com.jimmy.weather.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jimmy.weather.data.model.Location
import com.jimmy.weather.data.network.Resource
import com.jimmy.weather.data.persistence.ImageEntity
import com.jimmy.weather.data.repository.WeatherDetailRepository
import kotlinx.coroutines.launch

class WeatherDetailViewModel(private val repository: WeatherDetailRepository) : ViewModel() {

    val location = liveData {
        emit(Resource.Loading())
        emit(repository.getLocation())
    }

    fun getWeather(location: Location) = liveData {
        emit(repository.getWeather(location))
    }

    fun insertImage(image: ImageEntity) {
        viewModelScope.launch {
            repository.insertImage(image)
        }
    }
}