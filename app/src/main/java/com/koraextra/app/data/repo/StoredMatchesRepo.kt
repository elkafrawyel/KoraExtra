package com.koraextra.app.data.repo

import androidx.lifecycle.LiveData
import com.cobonee.app.utily.DataResource
import com.cobonee.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.utily.Injector

class StoredMatchesRepo(private val appDatabase: AppDatabase) {

    suspend fun getStoredMatches(go: String): DataResource<LiveData<List<MatchModel>>> {
        val matchesList = appDatabase.myDao().getMatches()
        return DataResource.Success(matchesList)
    }

}