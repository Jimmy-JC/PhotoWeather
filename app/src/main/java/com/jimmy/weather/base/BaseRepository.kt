package com.jimmy.weather.base

import com.jimmy.weather.data.network.Resource
import com.jimmy.weather.data.network.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

open class BaseRepository {

    suspend fun <T> handleResponse(apiMethod: suspend () -> T): Resource<T> =
        withContext(Dispatchers.IO) {
            try {
                ResponseHandler.handleSuccess(apiMethod())
            } catch (ex: Exception) {
                ResponseHandler.handleException(ex)
            }
        }

}