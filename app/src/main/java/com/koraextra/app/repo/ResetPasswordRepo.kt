package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.auth.*
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class ResetPasswordRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun resetPassword(resetPasswordBody: ResetPasswordBody): DataResource<ResetPasswordResponse> {
        return safeApiCall(
            call = { resetPasswordCall(resetPasswordBody) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun resetPasswordCall(resetPasswordBody: ResetPasswordBody): DataResource<ResetPasswordResponse> {
        val response = retrofitApiService.resetPasswordAsync(resetPasswordBody).await()
        return DataResource.Success(response)
    }

}