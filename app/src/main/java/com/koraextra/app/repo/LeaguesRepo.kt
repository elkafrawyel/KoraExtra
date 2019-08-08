package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.LeagueModel
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class LeaguesRepo(private val retrofitApiService: RetrofitApiService) {

    suspend fun getLeagues(go: String): DataResource<List<LeagueModel>> {
        return safeApiCall(
            call = { leaguesCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun leaguesCall(go: String): DataResource<List<LeagueModel>> {
        val response = retrofitApiService.getLeaguesAsync(go).await()
        val leagues  = response.leaguesApi!!.leagues
        return DataResource.Success(leagues)
    }


}