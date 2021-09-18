package com.udacity.asteroidradar.data.database.picture

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture ORDER BY date DESC LIMIT 1")
    fun getPicture(): LiveData<PictureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pictureEntity: PictureEntity)

    @Query("DELETE FROM Picture WHERE date <= (SELECT date FROM (SELECT date FROM Picture ORDER BY date DESC LIMIT 1 OFFSET 2))")
    suspend fun deleteAllPictures()
}