package com.example.mynews

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class MyNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}