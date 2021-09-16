package com.udacity.asteroidradar.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Asteroid")
data class AsteroidEntity(
    @PrimaryKey
    @ColumnInfo(name = "asteroidID")
    val id: Long,
    val codename: String,
    @TypeConverters(DateTypeConverter::class)
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)