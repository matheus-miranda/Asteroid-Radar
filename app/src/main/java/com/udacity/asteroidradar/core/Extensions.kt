package com.udacity.asteroidradar.core

import androidx.lifecycle.MutableLiveData

/**
 * Default initial value for a MutableLiveData
 */
fun <T : Any?> MutableLiveData<T>.defaultValue(initialValue: T) =
    apply { setValue(initialValue) }