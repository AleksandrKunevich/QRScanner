package com.example.qrscanner.domain.di

import com.example.qrscanner.domain.ElementInteractorImpl
import com.example.qrscanner.domain.ElementViewModel
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideElementViewModel(interactor: ElementInteractorImpl): ElementViewModel =
        ElementViewModel(interactor)
}