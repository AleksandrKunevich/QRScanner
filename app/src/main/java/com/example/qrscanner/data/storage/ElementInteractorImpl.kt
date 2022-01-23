package com.example.qrscanner.data.storage

import com.example.qrscanner.domain.ElementInteractor
import com.example.qrscanner.domain.ElementQR
import com.example.qrscanner.domain.utils.toElementEntity
import com.example.qrscanner.domain.utils.toElementQR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ElementInteractorImpl @Inject constructor(
    private val elementDao: ElementDao
) : ElementInteractor {

    override suspend fun getAll(): List<ElementQR> =
        withContext(Dispatchers.IO) {
            elementDao.getAll().map {
                it.toElementQR()
            }
        }

    override suspend fun insert(elementQR: ElementQR) =
        withContext(Dispatchers.IO) {
            elementDao.insertEntity(elementQR.toElementEntity())
        }

    override suspend fun delete(elementQR: ElementQR) =
        withContext(Dispatchers.IO) {
            elementDao.deleteEntity(elementQR.toElementEntity())
        }

}