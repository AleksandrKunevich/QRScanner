package com.example.qrscanner.data.storage.utils

import androidx.room.TypeConverter
import java.util.*

object DateConverter {
    @TypeConverter
    fun timestampFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date {
        return Date(timestamp)
    }
}