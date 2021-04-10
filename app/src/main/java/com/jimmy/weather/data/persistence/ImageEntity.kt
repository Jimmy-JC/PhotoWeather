package com.jimmy.weather.data.persistence

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ImageEntity(
    val path: String,
    val icon: String,
    val placeName: String,
    val weather: String,
    val condition: String,
    val temperature: Int
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    val iconUrl
        get() = "http://openweathermap.org/img/wn/$icon@2x.png"
}
