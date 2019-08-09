package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class NewsRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun getNews(go: String,leagueId: String): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsCall(go,leagueId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsCall(go: String,leagueId: String): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getNewsAsync(go,leagueId).await()
        return DataResource.Success(response)
    }

}