package com.udacity.asteroidradar.data.database

import androidx.room.TypeConverter
import com.udacity.asteroidradar.core.Constants
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Converts from a Date type to and from String, so we can order our results in the DAO.
 */
class DateTypeConverter {

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        date?.let {
            return getDateFormat().format(date)
        }
        return null
    }

    @TypeConverter
    fun fromStringToDate(dateString: String?): Date? {
        dateString?.let {
            try {
                return getDateFormat().parse(dateString)
            } catch (e: ParseException) {
                Timber.e(e.message.toString())
            }
        }
        return null
    }

    /**
     * Helper function
     */
    private fun getDateFormat(): SimpleDateFormat {
        return SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    }
}