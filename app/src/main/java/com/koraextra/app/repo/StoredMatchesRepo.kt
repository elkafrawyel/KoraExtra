package com.koraextra.app.repo

import androidx.lifecycle.LiveData
import com.koraextra.app.utily.DataResource
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.storage.local.AppDatabase

class StoredMatchesRepo(private val appDatabase: AppDatabase) {

    suspend fun getStoredMatches(go: String): DataResource<LiveData<List<MatchModel>>> {
        val matchesList = appDatabase.myDao().getMatches()
        return DataResource.Success(matchesList)
    }
    suspend fun getStoredMatchById(id: Int): DataResource<LiveData<MatchModel>> {
        val match = appDatabase.myDao().getMatchById(id)
        return DataResource.Success(match)
    }

}