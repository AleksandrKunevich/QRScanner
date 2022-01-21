package com.example.qrscanner

import android.content.Context
import com.example.qrscanner.di.RoomModule
import com.example.qrscanner.presentation.*
import com.example.qrscanner.utils.SaveBitmap
import com.example.qrscanner.utils.SaveBitmapImpl
import com.example.qrscanner.utils.di.SaveBitmapModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RoomModule::class,
        SaveBitmapModule::class
    ]
)

interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun bindContext(context: Context): Builder
    }

    fun inject(target: RoomFragment)
    fun inject(target: CameraFragment)
    fun inject(target: ScannerFragment)
    fun inject(target: MainActivity)
    fun inject(target: SaveBitmap)
    fun inject(target: RealtimeScannerFragment)
    fun inject(target: SaveBitmapImpl)

}