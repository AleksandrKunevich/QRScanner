package com.example.qrscanner.domain

import com.example.qrscanner.data.storage.ElementEntity

interface ElementInteractor {

    suspend fun getAll(): List<ElementEntity>

    suspend fun insertEntity(elementEntity: ElementEntity)

    suspend fun deleteEntity(elementEntity: ElementEntity)
}