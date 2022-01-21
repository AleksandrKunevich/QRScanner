package com.example.qrscanner.di

import android.content.Context
import androidx.room.Room
import com.example.qrscanner.domain.ElementInteractor
import com.example.qrscanner.domain.ElementInteractorImpl
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.storage.AppDataBaseRoom
import com.example.qrscanner.storage.ElementDao
import dagger.Module
import dagger.Provides

@Module
object RoomModule {

    @Provides
    fun provideDataBaseRoom(context: Context): AppDataBaseRoom =
        Room.databaseBuilder(
            context,
            AppDataBaseRoom::class.java,
            "room"
        ).build()

    @Provides
    fun provideElementDao(database: AppDataBaseRoom): ElementDao =
        database.getElementDao()

    @Provides
    fun provideElementInteractorImpl(interactor: ElementInteractorImpl): ElementInteractor =
        interactor

    @Provides
    fun provideElementViewModel(interactor: ElementInteractorImpl): ElementViewModel =
        ElementViewModel(interactor)
}