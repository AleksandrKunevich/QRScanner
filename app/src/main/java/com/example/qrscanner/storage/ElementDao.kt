package com.example.qrscanner.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ElementDao {
    @Query("SELECT * FROM room")
    fun getAll(): List<ElementEntity>

    @Insert
    fun insertEntity(elementEntity: ElementEntity)

    @Delete
    fun deleteEntity(elementEntity: ElementEntity)
}