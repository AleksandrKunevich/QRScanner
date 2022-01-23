package com.example.qrscanner.data.storage

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "room")
data class ElementEntity(

    @PrimaryKey
    var uid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "bitmap")
    val bitmap: Bitmap,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "time")
    val time: Long
)