package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.core.DateUtils
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromDbUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromNetworkUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.SaveAsteroidsToDbUseCase
import com.udacity.asteroidradar.domain.usecases.picture.CacheNetworkPictureUseCase
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromDbUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    getPictureFromDb: GetPictureFromDbUseCase,
    private val cacheNetworkPictureUseCase: CacheNetworkPictureUseCase,
    private val getAsteroidsFromNetworkUseCase: GetAsteroidsFromNetworkUseCase,
    getAsteroidsFromDbUseCase: GetAsteroidsFromDbUseCase,
    private val saveAsteroidsToDbUseCase: SaveAsteroidsToDbUseCase
) : ViewModel() {

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetails: LiveData<Asteroid?> get() = _navigateToAsteroidDetails

    private val dateUtils by lazy { DateUtils() }

    init {
        refreshPictureCache()
        refreshAsteroidCache()
    }

    val picture = getPictureFromDb()

    val asteroid = getAsteroidsFromDbUseCase()

    private fun refreshPictureCache() {
        viewModelScope.launch {
            cacheNetworkPictureUseCase("DEMO_KEY")
        }
    }

    private fun refreshAsteroidCache() {
        viewModelScope.launch {
            val asteroidList = getAsteroidsFromNetworkUseCase(
                dateUtils.getTodayDate(),
                dateUtils.getWeekFromNowDate(),
                "DEMO_KEY"
            )
            saveAsteroidsToDbUseCase(asteroidList.toTypedArray())
        }
    }

    fun navigateToDetails(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun navigateToDetailsComplete() {
        _navigateToAsteroidDetails.value = null
    }
}