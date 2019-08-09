package com.koraextra.app.repo

import androidx.lifecycle.LiveData
import com.koraextra.app.utily.DataResource
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.storage.local.AppDatabase

class StoredMatchesRepo(private val appDatabase: AppDatabase) {

    fun getStoredMatches(date: String): DataResource<LiveData<List<MatchModel>>> {
        val matchesList = appDatabase.myDao().getMatches(date)
        return DataResource.Success(matchesList)
    }

    fun getStoredLiveMatches(types: Array<Int>): DataResource<LiveData<List<MatchModel>>> {
        val matchesList = appDatabase.myDao().getLiveMatches(types)
        return DataResource.Success(matchesList)
    }

    fun getStoredMatchById(id: Int): DataResource<LiveData<MatchModel>> {
        val match = appDatabase.myDao().getMatchById(id)
        return DataResource.Success(match)
    }

    fun getStoredLeagueMatches(id: Int): DataResource<LiveData<List<MatchModel>>> {
        val match = appDatabase.myDao().getMatchByLeagueId(id)
        return DataResource.Success(match)
    }
}