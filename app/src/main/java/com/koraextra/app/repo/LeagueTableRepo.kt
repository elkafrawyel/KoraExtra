package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.models.LeagueTableResponse
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class LeagueTableRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun getLeagueTable(go: String): DataResource<LeagueTableResponse> {
        return safeApiCall(
            call = { leagueTableCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun leagueTableCall(go: String): DataResource<LeagueTableResponse> {
        val response = retrofitApiService.getLeaguesTableAsync(go).await()
        return DataResource.Success(response)
    }

}