package com.koraextra.app

import android.app.Application
import com.blankj.utilcode.util.Utils

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}