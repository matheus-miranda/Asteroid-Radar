package com.udacity.asteroidradar.presentation.di

import com.udacity.asteroidradar.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MainViewModel(
            getPictureFromDb = get(),
            getPictureFromNetworkUseCase = get(),
            savePictureToDbUseCase = get(),
        )
    }
}