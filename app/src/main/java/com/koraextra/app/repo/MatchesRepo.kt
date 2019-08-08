package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
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
        appDatabase.myDao().deleteMatches()
        return if(response.response?.count!!>0){
            val matches= response.response.matchModels
    //            matches?.forEach {
    //                it?.awayTeam!!.fixtureId = it.fixtureId!!
    //                it.homeTeam!!.fixtureId = it.fixtureId
    //                it.score!!.fixtureId = it.fixtureId
    //            }
            appDatabase.myDao().insertMatches(matches as List<MatchModel>)
            DataResource.Success(true)
        }else{

            DataResource.Success(false)
        }
    }


}