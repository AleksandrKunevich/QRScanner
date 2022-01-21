package com.example.qrscanner.data

import android.graphics.Bitmap
import java.util.*

data class Element(
    val bitmap: Bitmap,
    val date: Date,
    val time: Long
)
