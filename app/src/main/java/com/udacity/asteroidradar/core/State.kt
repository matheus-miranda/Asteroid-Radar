package com.udacity.asteroidradar.core

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val error: Throwable) : State<Nothing>()
}
