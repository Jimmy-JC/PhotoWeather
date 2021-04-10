package com.jimmy.weather.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.jimmy.weather.data.network.Resource
import com.jimmy.weather.util.snack

open class BaseActivity : AppCompatActivity() {

    var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootView = findViewById(android.R.id.content)
    }

    fun start(activity: Class<out BaseActivity>, extras: Bundle? = null) {
        val intent = Intent(this, activity)
        extras?.let { intent.putExtras(it) }
        startActivity(intent)
    }

    fun <T> observe(liveData: LiveData<Resource<T>>, result: (data: T) -> Unit) {
        liveData.observe(this, {
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> { hideLoading()
                    it.data?.let { value -> result(value) }
                }
                is Resource.Error -> { hideLoading()
                    it.message?.let { e -> showMessage(e) }
                }
            }
        })
    }

    fun showMessage(message: String) {
        rootView?.snack(message)
    }

    open fun showLoading() {
        // Show generic loading dialog
        // With ability to be overridden from child activity
        // To implement custom loading behaviour
    }

    open fun hideLoading() {
        // Same as showLoading()
    }

}