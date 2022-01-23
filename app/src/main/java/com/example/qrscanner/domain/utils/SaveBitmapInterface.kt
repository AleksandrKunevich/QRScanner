package com.example.qrscanner.domain.utils

import android.content.Context
import android.graphics.Bitmap

interface SaveBitmapInterface {

    fun saveBitmapInStorage(bitmap: Bitmap, context: Context)
}