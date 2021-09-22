package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.Constants
import com.udacity.asteroidradar.core.DateUtils
import com.udacity.asteroidradar.core.defaultValue
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.usecases.asteroid.AsteroidFilterUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.CacheNetworkAsteroidsUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAllAsteroidsFromDbUseCase
import com.udacity.asteroidradar.domain.usecases.picture.CacheNetworkPictureUseCase
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromDbUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val cacheNetworkPictureUseCase: CacheNetworkPictureUseCase,
    private val cacheNetworkAsteroidsUseCase: CacheNetworkAsteroidsUseCase,
    getPictureFromDb: GetPictureFromDbUseCase,
    getAllAsteroidsFromDbUseCase: GetAllAsteroidsFromDbUseCase,
    asteroidFilterUseCase: AsteroidFilterUseCase
) : ViewModel() {

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetails: LiveData<Asteroid?> get() = _navigateToAsteroidDetails

    private val dateUtils by lazy { DateUtils() }

    // Filter value from the user selected overflow menu, defaults to showing the current week
    private val _asteroidFilter = MutableLiveData<String>().defaultValue(dateUtils.getDatesForTheWeek())

    // LiveData<Picture> from the database
    val picture = getPictureFromDb()

    // Only the filtered list is exposed to the Fragment to be observed
    private val _asteroids = getAllAsteroidsFromDbUseCase()
    val filteredAsteroids = asteroidFilterUseCase(_asteroids, _asteroidFilter)

    init {
        refreshPictureCache()
        refreshAsteroidCache()
    }

    private fun refreshPictureCache() {
        viewModelScope.launch {
            cacheNetworkPictureUseCase(Constants.API_KEY)
        }
    }

    private fun refreshAsteroidCache() {
        viewModelScope.launch {
            cacheNetworkAsteroidsUseCase(
                dateUtils.getTodayDate(), dateUtils.getWeekFromNowDate(), Constants.API_KEY
            )
        }
    }

    fun navigateToDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun navigateToDetailsComplete() {
        _navigateToAsteroidDetails.value = null
    }

    /**
     * Receives the filter value from the overflow menu in the fragment
     */
    fun updateFilter(filter: String) {
        filter.let {
            _asteroidFilter.value = it
        }
    }
}