package com.udacity.asteroidradar.core

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    fun getTodayDate(): String {
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }

    fun getWeekFromNowDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
        return dateFormat.format(calendar.time)
    }

}