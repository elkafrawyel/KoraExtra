package com.koraextra.app.data.repo

import com.cobonee.app.utily.DataResource
import com.cobonee.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.MatchResponse
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class MatchesRepo(
   private val retrofitApiService: RetrofitApiService,
   private val appDatabase: AppDatabase
) {

    suspend fun getMatches(go: String): DataResource<Boolean> {
        return safeApiCall(
            call = { matchCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchCall(go: String): DataResource<Boolean> {
        val response = retrofitApiService.getMatchesAsync(go).await()
        if(response.response?.count!!>0){
            val matches= response.response.matchModels
//            matches?.forEach {
//                it?.awayTeam!!.fixtureId = it.fixtureId!!
//                it.homeTeam!!.fixtureId = it.fixtureId
//                it.score!!.fixtureId = it.fixtureId
//            }
            appDatabase.myDao().insertMatches(matches as List<MatchModel>)
            return DataResource.Success(true)
        }else{
            return DataResource.Success(false)
        }
    }


}