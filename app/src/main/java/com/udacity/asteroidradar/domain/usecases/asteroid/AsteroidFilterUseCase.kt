package com.udacity.asteroidradar.domain.usecases.asteroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.udacity.asteroidradar.domain.model.Asteroid

class AsteroidFilterUseCase {

    operator fun invoke(
        list: LiveData<List<Asteroid>>,
        filter: LiveData<String>
    ): LiveData<List<Asteroid>> =
        Transformations.switchMap(filter) {
            list.map { list ->
                filter.value?.let {
                    list.filter { asteroid ->
                        val query = filter.value.toString()
                        with(asteroid) {
                            (closeApproachDate.contains(query) || closeApproachDate in query)
                        }
                    }
                }
            }
        }
}