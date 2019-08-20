package com.koraextra.app.data.storage.remote

import com.koraextra.app.data.models.*
import com.koraextra.app.data.models.EventsResponse
import com.koraextra.app.data.models.MatchResponse
import com.koraextra.app.data.models.PlayersResponse
import com.koraextra.app.data.models.auth.LoginBody
import com.koraextra.app.data.models.auth.LoginResponse
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    fun getMatchTopsAsync(
        @Query("go") go: String
    ): Deferred<PlayersResponse>

    @GET("apigo.php")
    fun getMatchTopsAsync(
        @Query("go") go: String,
        @Query("league_id") league_id: Int
    ): Deferred<PlayersResponse>

    @GET("apigo.php")
    fun getLeaguesAsync(
        @Query("go") go: String
    ): Deferred<LeaguesResponse>

    @GET("apigo.php")
    fun getLeaguesMatchesAsync(
        @Query("go") go: String
    ): Deferred<MatchResponse>

    @GET("apigo.php")
    fun getSeasonsAsync(
        @Query("go") go: String
    ): Deferred<SeasonsResponse>

    @POST("api/register")
    fun registerAsync(
        @Body registerBody: RegisterBody
    ): Deferred<RegisterResponse>

    @POST("api/doLogin")
    fun loginAsync(
        @Body loginBody: LoginBody
    ): Deferred<LoginResponse>

    //====================news=============================
    @GET("apigo.php")
    fun getAllNewsAsync(
        @Query("go") go: String,
        @Query("all") yes: String
    ): Deferred<List<KoraNewsModel>>

    @GET("apigo.php")
    fun getLeagueNewsAsync(
        @Query("go") go: String,
        @Query("league_id") leagueId: String
    ): Deferred<List<KoraNewsModel>>

    @GET("apigo.php")
    fun getMatchNewsAsync(
        @Query("go") go: String,
        @Query("match_id") matchId: String
    ): Deferred<List<KoraNewsModel>>

    @GET("apigo.php")
    fun getTeamNewsAsync(
        @Query("go") go: String,
        @Query("team_id") teamId: String
    ): Deferred<List<KoraNewsModel>>

    @GET("apigo.php")
    fun getPlayerNewsAsync(
        @Query("go") go: String,
        @Query("player_id") playerId: String
    ): Deferred<List<KoraNewsModel>>


}