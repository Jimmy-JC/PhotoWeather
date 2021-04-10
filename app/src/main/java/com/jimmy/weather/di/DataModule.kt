package com.jimmy.weather.di

import androidx.room.Room
import com.jimmy.weather.BuildConfig
import com.jimmy.weather.data.network.WeatherAPI
import com.jimmy.weather.data.persistence.ImageDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherAPI::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), ImageDatabase::class.java, "image.db")
            .build().imageDao()
    }
}
