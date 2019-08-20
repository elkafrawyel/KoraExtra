package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.auth.LoginBody
import com.koraextra.app.data.models.auth.LoginResponse
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class LoginRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun login(loginBody: LoginBody): DataResource<LoginResponse> {
        return safeApiCall(
            call = { loginCall(loginBody) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun loginCall(loginBody: LoginBody): DataResource<LoginResponse> {
        val response = retrofitApiService.loginAsync(loginBody).await()
        return DataResource.Success(response)
    }

}