package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class RegisterRepo(
    private val retrofitApiService: RetrofitApiService) {

    suspend fun register(registerBody: RegisterBody): DataResource<RegisterResponse> {
        return safeApiCall(
            call = { registerCall(registerBody) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun registerCall(registerBody: RegisterBody): DataResource<RegisterResponse> {
        val response = retrofitApiService.registerAsync(registerBody).await()
        return DataResource.Success(response)
    }

}