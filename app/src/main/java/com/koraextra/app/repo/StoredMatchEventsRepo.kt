package com.koraextra.app.repo

import androidx.lifecycle.LiveData
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.utily.DataResource
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.storage.local.AppDatabase

class StoredMatchEventsRepo(private val appDatabase: AppDatabase) {

    suspend fun getStoredMatchEvents(fixtureId: Int): DataResource<LiveData<List<EventModel>>> {
        val matchesList = appDatabase.myDao().getMatchEvents(fixtureId)
        return DataResource.Success(matchesList)
    }

}