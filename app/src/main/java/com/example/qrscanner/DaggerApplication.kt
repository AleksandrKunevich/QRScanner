package com.example.qrscanner

import android.app.Application
import com.example.qrscanner.di.ApplicationComponent

class DaggerApplication : Application() {

    companion object {
        var appComponent: ApplicationComponent? = null
    }
}