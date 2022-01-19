package com.example.qrscanner.di

import android.content.Context
import androidx.room.Room
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.storage.AppDataBaseRoom
import com.example.qrscanner.storage.ElementDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBaseRoom(app: Context): AppDataBaseRoom =
        Room.databaseBuilder(
            app,
            AppDataBaseRoom::class.java,
            "room"
        ).build()

    @Singleton
    @Provides
    fun provideElementDao(database: AppDataBaseRoom): ElementDao =
        database.getElementDao()

    @Singleton
    @Provides
    fun provideElementInteractorImpl(interactor: ElementInteractorImpl): ElementInteractor =
        interactor

    @Singleton
    @Provides
    fun provideElementViewModel(roomRepository: ElementInteractorImpl): ElementViewModel =
        ElementViewModel(roomRepository)


}