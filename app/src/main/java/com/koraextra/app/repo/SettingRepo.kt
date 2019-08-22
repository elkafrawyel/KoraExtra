package com.koraextra.app.repo

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.*
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class SettingRepo(
    private val retrofitApiService: RetrofitApiService
) {

    //==================================get==============================
    suspend fun setting(name:String,status:Int): DataResource<SettingResponse> {
        return safeApiCall(
            call = { getSettingCall(name,status) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun getSettingCall(name:String,status:Int): DataResource<SettingResponse> {
        val response = retrofitApiService.settingAsync(name,status).await()
        return DataResource.Success(response)
    }


}