package com.example.qrscanner.di

import com.example.qrscanner.storage.ElementDao
import com.example.qrscanner.storage.ElementEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ElementInteractorImpl @Inject constructor(
    private val elementDao: ElementDao
): ElementInteractor {
    override suspend fun getAll(): List<ElementEntity> =
        withContext(Dispatchers.IO) {
            elementDao.getAll()
        }

    override suspend fun insertEntity(elementEntity: ElementEntity) =
        withContext(Dispatchers.IO) {
            elementDao.insertEntity(elementEntity)
        }

    override suspend fun deleteEntity(elementEntity: ElementEntity) =
        withContext(Dispatchers.IO) {
            elementDao.insertEntity(elementEntity)
        }
}