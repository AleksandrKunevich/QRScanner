package com.example.qrscanner.presentation.recycler

import android.graphics.Bitmap

interface ElementClickListener {
    fun onItemClickListener(position: Int)
    fun onImageClickListener(bitmap: Bitmap)
}