package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.NotificationsResponse
import com.koraextra.app.data.models.auth.SocialBody
import com.koraextra.app.data.models.auth.SocialResponse
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class SocialLoginRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun login(socialBody: SocialBody): DataResource<SocialResponse> {
        return safeApiCall(
            call = { loginCall(socialBody) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun loginCall(socialBody: SocialBody): DataResource<SocialResponse> {
        val response = retrofitApiService.socialRegisterAsync(socialBody = socialBody).await()
        return DataResource.Success(response)
    }

}