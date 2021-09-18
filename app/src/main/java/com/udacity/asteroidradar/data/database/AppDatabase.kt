package com.udacity.asteroidradar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.data.database.asteroid.AsteroidDao
import com.udacity.asteroidradar.data.database.asteroid.AsteroidEntity
import com.udacity.asteroidradar.data.database.picture.PictureDao
import com.udacity.asteroidradar.data.database.picture.PictureEntity

@Database(
    entities = [AsteroidEntity::class, PictureEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureDao: PictureDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "db_nasa"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}