package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.*
import com.udacity.asteroidradar.core.State
import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromDbUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.GetAsteroidsFromNetworkUseCase
import com.udacity.asteroidradar.domain.usecases.asteroid.SaveAsteroidsToDbUseCase
import com.udacity.asteroidradar.domain.usecases.picture.CacheNetworkPictureUseCase
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromDbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPictureFromDb: GetPictureFromDbUseCase,
    private val cacheNetworkPictureUseCase: CacheNetworkPictureUseCase,
    private val getAsteroidsFromNetworkUseCase: GetAsteroidsFromNetworkUseCase,
    getAsteroidsFromDbUseCase: GetAsteroidsFromDbUseCase,
    private val saveAsteroidsToDbUseCase: SaveAsteroidsToDbUseCase
) : ViewModel() {

    private val _pictureState = MutableLiveData<State<Picture>>()
    val pictureState: LiveData<State<Picture>> get() = _pictureState

    init {
        refreshPictureCache()
        refreshAsteroidCache()
    }

    val picture = getPictureFromDb()

    val asteroid = getAsteroidsFromDbUseCase()

    private fun getPictures() {
        viewModelScope.launch {
            getPictureFromDb().asFlow().flowOn(Dispatchers.Main)
                .onStart {
                    _pictureState.value = State.Loading
                }
                .catch {
                    _pictureState.value = State.Error(it)
                }
                .collect {
                    _pictureState.value = State.Success(it)
                }
        }
    }

    private fun refreshPictureCache() {
        viewModelScope.launch {
            cacheNetworkPictureUseCase("DEMO_KEY")
        }
    }

    private fun refreshAsteroidCache() {
        viewModelScope.launch {
            val asteroidList = getAsteroidsFromNetworkUseCase("2021-09-19", "2021-09-26", "DEMO_KEY")
            saveAsteroidsToDbUseCase(asteroidList.toTypedArray())
        }
    }
}