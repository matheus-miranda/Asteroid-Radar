package com.udacity.asteroidradar.data.database.picture

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture")
    fun getPicture(): LiveData<PictureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(pictureEntity: PictureEntity)

    @Query("DELETE FROM Picture")
    suspend fun deleteAllPictures()
}