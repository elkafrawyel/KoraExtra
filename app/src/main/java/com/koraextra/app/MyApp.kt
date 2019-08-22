package com.koraextra.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.google.android.gms.ads.MobileAds

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)

        MobileAds.initialize(this,"ca-app-pub-7642057802414977~4668094555")


    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}