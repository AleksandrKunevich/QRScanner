package com.example.qrscanner.domain

import com.example.qrscanner.data.storage.ElementEntity

interface ElementInteractor {

    suspend fun getAll(): List<ElementQR>

    suspend fun insert(elementEntity: ElementQR)

    suspend fun delete(elementEntity: ElementQR)
}