package com.koraextra.app.repo

import com.koraextra.app.R
import com.koraextra.app.data.models.FireTokenRespons
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.safeApiCall

class SaveFireBaseTokenRepo(private val preferencesHelper: PreferencesHelper,private val retrofitApiService: RetrofitApiService) {

    suspend fun saveToken(token: String): DataResource<Boolean> {
        return safeApiCall(
            call = {
                preferencesHelper.fireBaseToken = token
                    saveTokenService(token)
                return@safeApiCall DataResource.Success(true)
            },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun saveTokenService(token: String) : FireTokenRespons {
        val response = retrofitApiService.saveTokenAsync(token).await()
        return  response
    }

}