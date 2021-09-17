package com.udacity.asteroidradar.domain.usecases.asteroid

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.repository.NasaRepository

class GetAsteroidsFromDbUseCase(private val repository: NasaRepository) {

    operator fun invoke(): LiveData<List<Asteroid>> {
        return repository.getAsteroidsFromDatabase()
    }
}