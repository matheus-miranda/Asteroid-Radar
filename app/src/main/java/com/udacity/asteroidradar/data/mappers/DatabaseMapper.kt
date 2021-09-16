package com.udacity.asteroidradar.data.mappers

import com.udacity.asteroidradar.data.database.asteroid.AsteroidEntity
import com.udacity.asteroidradar.domain.model.Asteroid

fun List<AsteroidEntity>.asDomainObject(): Array<Asteroid> {
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
    }.toTypedArray()
}