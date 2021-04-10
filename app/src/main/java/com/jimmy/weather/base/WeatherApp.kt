package com.jimmy.weather.base

import android.app.Application
import com.jimmy.weather.di.databaseModule
import com.jimmy.weather.di.networkModule
import com.jimmy.weather.di.repositoryModule
import com.jimmy.weather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(databaseModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}