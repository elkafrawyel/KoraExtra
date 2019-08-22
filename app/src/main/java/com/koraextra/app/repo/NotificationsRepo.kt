package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.NotificationsResponse
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class NotificationsRepo(
    private val retrofitApiService: RetrofitApiService,
    private val preferencesHelper: PreferencesHelper
) {

    suspend fun getNotifications(): DataResource<NotificationsResponse> {
        return safeApiCall(
            call = { notificationsCall() },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun notificationsCall(): DataResource<NotificationsResponse> {
        val response = retrofitApiService.notificationsAsync(preferencesHelper.token!!).await()
        return DataResource.Success(response)
    }


}