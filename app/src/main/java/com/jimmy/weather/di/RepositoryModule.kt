package com.jimmy.weather.di

import com.jimmy.weather.data.repository.WeatherDetailRepository
import com.jimmy.weather.data.repository.WeatherListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherListRepository(get()) }
    single { WeatherDetailRepository(get(), get()) }
}