package com.jimmy.weather.ui.list

import androidx.lifecycle.*
import com.jimmy.weather.data.persistence.ImageEntity
import com.jimmy.weather.data.repository.WeatherListRepository
import kotlinx.coroutines.launch

class WeatherListViewModel(private val repository: WeatherListRepository) : ViewModel() {

    val images = MutableLiveData<List<ImageEntity>>()

    fun getImages() {
        viewModelScope.launch {
            images.value = repository.getImages()
        }
    }

}