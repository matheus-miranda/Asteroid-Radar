package com.udacity.asteroidradar.data.database.asteroid

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :today ORDER BY closeApproachDate ASC")
    fun getWeeklyAsteroids(today: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM Asteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(vararg asteroids: AsteroidEntity)

    @Query("DELETE FROM Asteroid")
    suspend fun deleteAllAsteroids()
}