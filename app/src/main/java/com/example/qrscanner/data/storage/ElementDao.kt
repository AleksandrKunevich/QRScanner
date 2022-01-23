package com.example.qrscanner.data.storage

import androidx.room.*

@Dao
interface ElementDao {

    @Query("SELECT * FROM room")
    suspend fun getAll(): List<ElementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(elementEntity: ElementEntity)

    @Delete
    suspend fun deleteEntity(elementEntity: ElementEntity)
}