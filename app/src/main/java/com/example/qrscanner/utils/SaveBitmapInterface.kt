package com.example.qrscanner.utils

import android.content.Context
import android.graphics.Bitmap

interface SaveBitmapInterface {

    fun saveBitmapInStorage(bitmap: Bitmap, context: Context)
}