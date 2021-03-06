package com.example.qrscanner

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class DaggerApplication : Application() {

    companion object {
        var appComponent: ApplicationComponent? = null

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = initDagger()
    }

    private fun initDagger(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .bindContext(this)
            .build()
    }
}