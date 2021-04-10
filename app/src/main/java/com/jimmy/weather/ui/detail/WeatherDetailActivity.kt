package com.jimmy.weather.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jimmy.weather.R
import com.jimmy.weather.base.BaseActivity
import com.jimmy.weather.data.model.Weather
import com.jimmy.weather.data.persistence.ImageEntity
import com.jimmy.weather.util.ImageCapture
import com.jimmy.weather.util.addWatermark
import com.jimmy.weather.util.getShareIntent
import kotlinx.android.synthetic.main.activity_weather_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class WeatherDetailActivity : BaseActivity() {

    private lateinit var image: ImageEntity
    private lateinit var overlayBitmap: Bitmap
    private val imageCapture = ImageCapture(this)
    private val viewModel: WeatherDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)
        fetchIntentExtra()
        setClickListeners()
    }

    /**
     * Check for extra if exist
     * Render the image if any
     * Otherwise launch image capture
     */
    private fun fetchIntentExtra() {
        val imageExtra = intent.getParcelableExtra<ImageEntity>(IMAGE_EXTRA)
        if (imageExtra != null) {
            image = imageExtra
            renderImage()
        } else
            imageCapture.takePicture()
    }

    /**
     * Event handlers for the share and back buttons
     */
    private fun setClickListeners() {
        shareButton.setOnClickListener {
            if (!::overlayBitmap.isInitialized) return@setOnClickListener
            startActivity(Intent.createChooser(getShareIntent(this, overlayBitmap), null))
        }
        backButton.setOnClickListener { finish() }
    }

    /**
     * Obtain bitmap resource from the image file in the background thread
     * Add the weather details watermark over the image and display it
     * Clear the bitmap resource when view lifecycle end to prevent leaks
     */
    private fun renderImage() {
        Glide.with(this).asBitmap().load(File(image.path)).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                overlayBitmap = addWatermark(resource, getOverlayText())
                imageView.setImageBitmap(overlayBitmap)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                overlayBitmap.recycle()
            }
        })
    }

    /**
     * Get well formatted overlay string
     */
    private fun getOverlayText() = getString(
        R.string.image_overly,
        image.placeName, image.temperature, image.condition
    )

    /**
     * Once Image captured start fetching the weather process
     * Otherwise return to the home screen
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            getWeather()
        else
            finish()
    }


    /**
     * Fetch user location the get the weather for this coordinates
     * Save the result in image entity and render the image
     */
    private fun getWeather() {
        observe(viewModel.location) { location ->
            observe(viewModel.getWeather(location)) {
                saveImageEntity(it)
                renderImage()
            }
        }
    }

    /**
     * Fill image instance with API details
     * Save the image to the history database
     */
    private fun saveImageEntity(weather: Weather) {
        image = ImageEntity(
            path = imageCapture.imagePath,
            icon = weather.conditions.first().icon,
            placeName = weather.name,
            weather = weather.conditions.first().main,
            condition = weather.conditions.first().description,
            temperature = weather.main.temp.toInt()
        )
        viewModel.insertImage(image)
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val IMAGE_EXTRA = "image"
    }
}