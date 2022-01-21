package com.example.qrscanner.utils.di

import com.example.qrscanner.utils.SaveBitmapImpl
import com.example.qrscanner.utils.SaveBitmapInterface
import dagger.Module
import dagger.Provides

@Module
object SaveBitmapModule {

    @Provides
    fun provideSaveBitmapImpl(): SaveBitmapInterface = SaveBitmapImpl()
}