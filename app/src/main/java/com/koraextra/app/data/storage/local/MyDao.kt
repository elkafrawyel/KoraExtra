package com.koraextra.app.data.storage.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.data.models.MatchModel

@Dao
interface MyDao {

    @Query("SELECT * FROM MatchModel ORDER BY eventTimestamp")
    fun getMatches(): LiveData<List<MatchModel>>
    @Query("SELECT * FROM MatchModel WHERE eventDate LIKE :date")
    fun getMatches(date: String): LiveData<List<MatchModel>>

    @Query("SELECT * FROM MatchModel WHERE statuskey IN (:types)")
    fun getLiveMatches(types: Array<Int>): LiveData<List<MatchModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatches(item: List<MatchModel>)

    @Query("DELETE FROM MatchModel")
    fun deleteMatches()

    @Query("SELECT * FROM MatchModel WHERE fixtureId = :id")
    fun getMatchById(id: Int): LiveData<MatchModel>

    @Query("SELECT * FROM MatchModel WHERE leagueId = :id")
    fun getMatchByLeagueId(id: Int): LiveData<List<MatchModel>>

    @Query("SELECT * FROM MatchModel WHERE homeTeam = :home OR awayTeam= :away ")
    fun getMatchByTeam(away: String,home:String): LiveData<List<MatchModel>>

    @Query("SELECT * FROM EventModel WHERE fixtureId =:fixtureId ORDER BY elapsed DESC")
    fun getMatchEvents(fixtureId: Int): LiveData<List<EventModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatchEvents(item: List<EventModel>)
}