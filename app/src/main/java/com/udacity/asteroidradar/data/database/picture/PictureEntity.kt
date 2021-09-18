package com.udacity.asteroidradar.data.database.picture

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.udacity.asteroidradar.data.database.DateTypeConverter

@Entity(tableName = "Picture")
data class PictureEntity(
    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    @TypeConverters(DateTypeConverter::class)
    val date: String
)
