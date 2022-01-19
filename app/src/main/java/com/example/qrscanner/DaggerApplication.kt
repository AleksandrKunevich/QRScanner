package com.example.qrscanner

import android.app.Application
import android.content.Context
import com.example.qrscanner.di.ApplicationComponent
import com.example.qrscanner.di.DaggerApplicationComponent

class DaggerApplication : Application() {

    companion object {
        var appComponent: ApplicationComponent? = null
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
        context = this
    }

    private fun initDagger(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .bindContext(this@DaggerApplication)
            .build()
    }
}