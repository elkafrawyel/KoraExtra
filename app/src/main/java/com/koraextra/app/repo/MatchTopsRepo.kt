package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.PlayerModel
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class MatchTopsRepo(
    private val retrofitApiService: RetrofitApiService
) {

    suspend fun getPlayers(go: String): DataResource<List<PlayerModel>> {
        return safeApiCall(
            call = { matchTopsCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchTopsCall(go: String): DataResource<List<PlayerModel>> {
        val response = retrofitApiService.getMatchTopsAsync(go).await()
        val matchTops = response.api?.players!!
        return DataResource.Success(matchTops)

    }

    suspend fun getPlayers(go: String,leagueId:Int): DataResource<List<PlayerModel>> {
        return safeApiCall(
            call = { matchTopsCall(go,leagueId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchTopsCall(go: String,leagueId:Int): DataResource<List<PlayerModel>> {
        val response = retrofitApiService.getMatchTopsAsync(go,leagueId).await()
        val matchTops = response.api?.players!!
        return DataResource.Success(matchTops)

    }


}