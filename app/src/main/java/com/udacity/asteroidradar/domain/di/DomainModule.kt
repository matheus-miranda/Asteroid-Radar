package com.udacity.asteroidradar.domain.di

import com.udacity.asteroidradar.domain.usecases.asteroid.*
import com.udacity.asteroidradar.domain.usecases.picture.*
import org.koin.dsl.module

val domainModule = module {
    factory { CacheNetworkAsteroidsUseCase(repository = get()) }
    factory { DeleteAsteroidsFromDbUseCase(repository = get()) }
    factory { GetAsteroidsFromDbUseCase(repository = get()) }
    factory { GetAsteroidsFromNetworkUseCase(repository = get()) }
    factory { SaveAsteroidsToDbUseCase(repository = get()) }
    factory { CacheNetworkPictureUseCase(repository = get()) }
    factory { DeletePicturesFromDbUseCase(repository = get()) }
    factory { GetPictureFromDbUseCase(repository = get()) }
    factory { GetPictureFromNetworkUseCase(repository = get()) }
    factory { SavePictureToDbUseCase(repository = get()) }
    factory { AsteroidFilterUseCase() }
}