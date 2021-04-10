package com.jimmy.weather.data.network

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.Exception

class ResponseHandler {

    companion object {

        fun <T> handleSuccess(response: T) = Resource.Success(response)

        fun <T> handleException(ex: Exception) = Resource.Error<T>(getErrorMessage(ex))

        private fun getErrorMessage(ex: Exception) =
                when(ex) {
                    is IOException -> "Please check internet connection"
                    is HttpException -> getErrorForCode(ex.code())
                    is SocketTimeoutException -> "Request timeout"
                    else -> ex.message!!
                }

        private fun getErrorForCode(code: Int) =
                when(code) {
                    401 -> "Unauthorised access"
                    404 -> "Data not found"
                    500 -> "Internal server error"
                    else -> "Something went wrong"
                }
    }
}