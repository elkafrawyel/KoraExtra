package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.FavoriteBody
import com.koraextra.app.data.models.FavoriteResponse
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class SaveFireBaseTokenRepo(private val preferencesHelper: PreferencesHelper) {

    suspend fun saveToken(token: String): DataResource<Boolean> {
        return safeApiCall(
            call = {
                preferencesHelper.fireBaseToken = token
                return@safeApiCall DataResource.Success(true)
            },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

}