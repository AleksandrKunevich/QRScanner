package com.example.qrscanner.domain.di

import com.example.qrscanner.domain.utils.SaveBitmapImpl
import com.example.qrscanner.domain.utils.SaveBitmapInterface
import dagger.Module
import dagger.Provides

@Module
object SaveBitmapModule {

    @Provides
    fun provideSaveBitmapImpl(): SaveBitmapInterface = SaveBitmapImpl()
}