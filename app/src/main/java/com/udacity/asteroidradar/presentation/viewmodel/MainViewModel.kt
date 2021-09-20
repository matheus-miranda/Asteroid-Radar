package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.core.DateUtils
import com.udacity.asteroidradar.core.defaultValue
import com.udacity.asteroidradar.domain.model.Asteroid
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromDbUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromNetworkUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.SaveAsteroidsToDbUseCase
import com.udacity.asteroidradar.domain.usecases.picture.CacheNetworkPictureUseCase
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromDbUseCase
import kotlinx.coroutines.launch

private const val API_KEY = BuildConfig.API_KEY

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

    // Filter value from the user selected overflow menu, empty default to get the whole list
    private val _asteroidFilter = MutableLiveData<String>().defaultValue("")

    // LiveData<Picture> from the database
    val picture = getPictureFromDb()

    // Only the filtered list is exposed to the Fragment to be observed
    private val _asteroids = getAsteroidsFromDbUseCase(dateUtils.getTodayDate())
    val filteredAsteroids = applyFilter(_asteroids, _asteroidFilter)

    init {
        refreshPictureCache()
        refreshAsteroidCache()
    }

    private fun refreshPictureCache() {
        viewModelScope.launch {
            cacheNetworkPictureUseCase(API_KEY)
        }
    }

    private fun refreshAsteroidCache() {
        viewModelScope.launch {
            val asteroidList = getAsteroidsFromNetworkUseCase(
                dateUtils.getTodayDate(),
                dateUtils.getWeekFromNowDate(),
                API_KEY
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

    /**
     * Receives the filter value from the overflow menu in the fragment
     */
    fun updateFilter(filter: String) {
        filter.let {
            _asteroidFilter.value = it
        }
    }

    /**
     * Applies the filter from the menu to the list of asteroids
     *
     * @return LiveData<List<Asteroid>> observable list with the filter applied
     */
    private fun applyFilter(
        list: LiveData<List<Asteroid>>,
        filter: LiveData<String>
    ): LiveData<List<Asteroid>> =
        Transformations.switchMap(filter) {
            list.map { list ->
                filter.value?.let {
                    list.filter { asteroid ->
                        val query = filter.value.toString()
                        with(asteroid) {
                            closeApproachDate.contains(query)
                        }
                    }
                }
            }
        }
}