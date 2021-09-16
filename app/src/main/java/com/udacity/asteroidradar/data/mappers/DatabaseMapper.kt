package com.udacity.asteroidradar.data.mappers

import com.udacity.asteroidradar.data.database.asteroid.AsteroidEntity
import com.udacity.asteroidradar.data.database.picture.PictureEntity
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.model.Picture

fun List<AsteroidEntity>.asDomainObject(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            relativeVelocity = it.relativeVelocity,
            estimatedDiameter = it.estimatedDiameter,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun Array<Asteroid>.asDatabaseModel(): Array<AsteroidEntity> {
    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            relativeVelocity = it.relativeVelocity,
            estimatedDiameter = it.estimatedDiameter,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun PictureEntity.asDomainObject(): Picture {
    return Picture(
        title = this.title,
        mediaType = this.mediaType,
        url = this.url
    )
}

fun Picture.asDatabaseModel(): PictureEntity {
    return PictureEntity(
        title = this.title,
        url = this.url,
        mediaType = this.mediaType
    )
}