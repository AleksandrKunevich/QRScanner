package com.example.qrscanner.di

import android.content.Context
import com.example.qrscanner.RoomFragment
import com.example.qrscanner.presentation.CameraFragment
import com.example.qrscanner.presentation.MainActivity
import com.example.qrscanner.presentation.ScannerFragment
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
        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(target: RoomFragment)
    fun inject(target: CameraFragment)
    fun inject(target: ScannerFragment)
    fun inject(target: MainActivity)
}