package com.android.mvvm.data.typeconverters

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateAdapters {

    @ToJson
    @TypeConverter
    fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @FromJson
    @TypeConverter fun dateStampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}