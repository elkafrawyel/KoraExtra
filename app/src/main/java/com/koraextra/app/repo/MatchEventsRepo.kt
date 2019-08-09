package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class MatchEventsRepo(
   private val retrofitApiService: RetrofitApiService,
   private val appDatabase: AppDatabase
) {

    suspend fun getMatchEvents(go: String): DataResource<Boolean> {
        return safeApiCall(
            call = { matchEventsCall(go) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchEventsCall(go: String): DataResource<Boolean> {
        val response = retrofitApiService.getEventsAsync(go).await()
        return if(response.response?.results!!>0){
            val matchEvents= response.response.events
            appDatabase.myDao().insertMatchEvents(matchEvents as List<EventModel>)
            DataResource.Success(true)
        }else{

            DataResource.Success(false)
        }
    }


}