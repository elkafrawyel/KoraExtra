package com.koraextra.app.data.storage.remote

import com.koraextra.app.data.models.MatchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {



    @GET("apigo.php")
    fun getMatchesAsync(
        @Query("go") go: String
    ): Deferred<MatchResponse>
}