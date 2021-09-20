package com.udacity.asteroidradar.presentation.adapter

import com.udacity.asteroidradar.domain.model.Asteroid

class ClickHandler(private val clickListener: (asteroid: Asteroid) -> Unit) {

    fun onClick(asteroid: Asteroid) {
        return clickListener(asteroid)
    }
}