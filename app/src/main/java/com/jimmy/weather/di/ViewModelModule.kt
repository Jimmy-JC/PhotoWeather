package com.jimmy.weather.di

import com.jimmy.weather.ui.detail.WeatherDetailViewModel
import com.jimmy.weather.ui.list.WeatherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherListViewModel(get()) }
    viewModel { WeatherDetailViewModel(get()) }
}