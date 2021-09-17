package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.core.State
import com.udacity.asteroidradar.domain.model.Picture
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
    private val cachePictureUseCase: CacheNetworkPictureUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<State<Picture>>()
    val state: LiveData<State<Picture>> get() = _state

    init {
        refreshPictureCache()
        getPictures()
    }

    private fun getPictures() {
        viewModelScope.launch {
            getPictureFromDb().asFlow().flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    private fun refreshPictureCache() {
        viewModelScope.launch {
            cachePictureUseCase(BuildConfig.API_KEY)
        }
    }
}