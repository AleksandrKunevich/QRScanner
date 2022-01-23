package com.example.qrscanner

import android.content.Context
import com.example.qrscanner.data.storage.di.RoomModule
import com.example.qrscanner.domain.di.SaveBitmapModule
import com.example.qrscanner.domain.di.ViewModelModule
import com.example.qrscanner.presentation.*
import com.example.qrscanner.data.SaveBitmapImpl
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RoomModule::class,
        SaveBitmapModule::class,
        ViewModelModule::class

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
    fun inject(target: RealtimeScannerFragment)
    fun inject(target: SaveBitmapImpl)

}