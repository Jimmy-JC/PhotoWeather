package com.jimmy.weather.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import androidx.core.content.FileProvider
import com.jimmy.weather.BuildConfig
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

fun getShareIntent(context: Context, image: Bitmap): Intent {
    val file = getBitmapFile(context, image)
    val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/jpeg"
    }
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    return shareIntent
}

fun getBitmapFile(context: Context, bitmap: Bitmap): File {
    val file = File.createTempFile("temp", ".png",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
    val os = BufferedOutputStream(FileOutputStream(file))
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
    os.close()
    return file
}