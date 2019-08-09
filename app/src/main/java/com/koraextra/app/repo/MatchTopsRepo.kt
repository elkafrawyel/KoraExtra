package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.data.models.Player
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class MatchTopsRepo(
    private val retrofitApiService: RetrofitApiService
) {

    suspend fun getMatchTops(go: String): DataResource<List<Player>> {
        return safeApiCall(
            call = { matchTopsCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchTopsCall(go: String): DataResource<List<Player>> {
        val response = retrofitApiService.getMatchTopsAsync(go).await()
        val matchTops = response.api?.players!!
        return DataResource.Success(matchTops)

    }


}