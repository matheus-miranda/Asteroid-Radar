package com.udacity.asteroidradar.presentation.viewmodel

import androidx.lifecycle.*
import com.udacity.asteroidradar.core.State
import com.udacity.asteroidradar.domain.model.Picture
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromDbUseCase
import com.udacity.asteroidradar.domain.usecases.picture.GetPictureFromNetworkUseCase
import com.udacity.asteroidradar.domain.usecases.picture.SavePictureToDbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPictureFromDb: GetPictureFromDbUseCase,
    private val getPictureFromNetworkUseCase: GetPictureFromNetworkUseCase,
    private val savePictureToDbUseCase: SavePictureToDbUseCase,
) : ViewModel() {

    private val _pictureState = MutableLiveData<State<Picture>>()
    val pictureState: LiveData<State<Picture>> get() = _pictureState

    init {
        getPictures()
    }

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

    fun fetchFromNetwork() {
        viewModelScope.launch {
            //val picture = getPictureFromNetworkUseCase(BuildConfig.API_KEY)
            val picture = getPictureFromNetworkUseCase("DEMO_KEY")
            //deletePicturesFromDbUseCase()
            savePictureToDbUseCase(picture)
        }
    }
}