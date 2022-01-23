package com.example.qrscanner.presentation.recycler

import android.graphics.Bitmap
import com.example.qrscanner.domain.ElementQR

interface ElementClickListener {

    fun onItemClickListener(position: Int)

    fun onImageClickListener(bitmap: Bitmap)

    fun onImageDeleteClickListener(elementQR: ElementQR)
}