package com.koraextra.app.data.storage.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.koraextra.app.data.models.MatchModel

@Dao
interface MyDao {

    @Query("SELECT * FROM MatchModel")
    fun getMatches(): LiveData<List<MatchModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatches(item: List<MatchModel>)

    @Query("DELETE FROM MatchModel")
    fun deleteMatches()
}