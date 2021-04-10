package com.jimmy.weather.data.persistence

import androidx.room.*

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)

    @Query("SELECT * FROM ImageEntity")
    suspend fun getImages(): List<ImageEntity>

}