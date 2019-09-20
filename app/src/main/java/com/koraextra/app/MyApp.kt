package com.koraextra.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.google.android.gms.ads.MobileAds

class MyApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)

        MobileAds.initialize(this,"ca-app-pub-7642057802414977~4668094555")


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}