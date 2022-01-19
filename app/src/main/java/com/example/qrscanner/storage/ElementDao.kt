package com.example.qrscanner.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ElementDao {

    @Query("SELECT * FROM room")
    suspend fun getAll(): List<ElementEntity>

    @Insert
    suspend fun insertEntity(elementEntity: ElementEntity)

    @Delete
    suspend fun deleteEntity(elementEntity: ElementEntity)
}