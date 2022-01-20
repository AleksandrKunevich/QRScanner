package com.example.qrscanner.di

import android.content.Context
import com.example.qrscanner.presentation.RoomFragment
import com.example.qrscanner.presentation.CameraFragment
import com.example.qrscanner.presentation.MainActivity
import com.example.qrscanner.presentation.ScannerFragment
import com.example.qrscanner.utils.SaveBitmap
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RoomModule::class
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

}