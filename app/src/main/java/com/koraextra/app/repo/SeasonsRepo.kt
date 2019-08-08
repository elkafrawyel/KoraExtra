package com.koraextra.app.repo

import com.koraextra.app.R
import com.koraextra.app.data.models.Seasons
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.safeApiCall

class SeasonsRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun getSeasons(): DataResource<Seasons> {
        return safeApiCall(
            call = { seasonsCall() },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun seasonsCall(): DataResource<Seasons> {
        val response = retrofitApiService.getSeasonsAsync("seasons").await()
        return DataResource.Success(response.seasons)
    }


}