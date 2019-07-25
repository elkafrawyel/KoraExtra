package com.cezma.store.utiles

import com.koraextra.app.MyApp
import com.koraextra.app.data.storage.local.PreferencesHelper


object Injector {

    fun getApplicationContext() = MyApp.instance
    fun getPreferenceHelper() = PreferencesHelper(getApplicationContext())


}