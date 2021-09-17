package com.udacity.asteroidradar.domain.usecases.picture

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.repository.NasaRepository

class GetPictureFromDbUseCase(private val repository: NasaRepository) {

    operator fun invoke(): LiveData<Picture> {
        return repository.getPictureFromDatabase()
    }
}