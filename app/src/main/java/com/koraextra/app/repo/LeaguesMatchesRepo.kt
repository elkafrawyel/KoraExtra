package com.koraextra.app.repo

import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.Injector
import com.koraextra.app.utily.safeApiCall
import java.util.ArrayList

class LeaguesMatchesRepo(
    private val retrofitApiService: RetrofitApiService,
    private val appDatabase: AppDatabase
) {

    suspend fun getLeagueMatches(go: String): DataResource<Boolean> {
        return safeApiCall(
            call = { leagueMatchCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun leagueMatchCall(go: String): DataResource<Boolean> {
        val response = retrofitApiService.getLeaguesMatchesAsync(go).await()
        return if (response.response?.count!! > 0) {
            val matches = response.response.matchModels
            appDatabase.myDao().insertMatches(matches as List<MatchModel>)
            DataResource.Success(true)
        } else {
            DataResource.Success(false)
        }
    }
}