package com.udacity.asteroidradar.domain.usecases.picture

import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.repository.NasaRepository

class SavePictureToDbUseCase(private val repository: NasaRepository) {

    suspend operator fun invoke(picture: Picture) {
        repository.savePictureToDatabase(picture)
    }
}