package com.koraextra.app.data.storage.remote

import com.koraextra.app.data.models.EventsResponse
import com.koraextra.app.data.models.LeaguesResponse
import com.koraextra.app.data.models.MatchResponse
import com.koraextra.app.data.models.SeasonsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {


    @GET("apigo.php")
    fun getMatchesAsync(
        @Query("go") go: String
    ): Deferred<MatchResponse>


    @GET("apigo.php")
    fun getEventsAsync(
        @Query("go") go: String
    ): Deferred<EventsResponse>

    @GET("apigo.php")
    fun getLeaguesAsync(
        @Query("go") go: String
    ): Deferred<LeaguesResponse>

    @GET("apigo.php")
    fun getSeasonsAsync(
        @Query("go") go: String
    ): Deferred<SeasonsResponse>


}