package com.jimmy.weather.util

import android.app.Activity
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.jimmy.weather.BuildConfig
import com.jimmy.weather.ui.detail.WeatherDetailActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageCapture(private val activity: Activity) {

    lateinit var imagePath: String

    fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity.packageManager)?.also {
                val photoFile = try { createImageFile() } catch (ex: IOException) { null }
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                            activity, BuildConfig.APPLICATION_ID + ".provider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    activity.startActivityForResult(
                        takePictureIntent,
                        WeatherDetailActivity.REQUEST_IMAGE_CAPTURE
                    )
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            imagePath = absolutePath
        }
    }

}