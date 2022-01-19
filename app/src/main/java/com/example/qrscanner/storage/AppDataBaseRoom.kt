package com.example.qrscanner.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.qrscanner.storage.utils.BitMapConverter
import com.example.qrscanner.storage.utils.DateConverter
import com.example.qrscanner.storage.utils.UUIDConverter

@Database(
    entities = [ElementEntity::class],
    version = AppDataBaseRoom.VERSION,
    exportSchema = true
)

@TypeConverters
    (
    UUIDConverter::class,
    DateConverter::class,
    BitMapConverter::class
)

abstract class AppDataBaseRoom : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getElementDao(): ElementDao
}