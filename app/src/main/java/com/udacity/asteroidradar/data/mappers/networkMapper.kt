package com.udacity.asteroidradar.data.mappers

import com.udacity.asteroidradar.data.remote.dto.PictureOfDay
import com.udacity.asteroidradar.domain.model.Picture

fun PictureOfDay.asDomainObject(): Picture {
    return Picture(
        title = title,
        mediaType = mediaType,
        url = url
    )
}