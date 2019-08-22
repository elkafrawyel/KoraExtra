package com.koraextra.app.data.storage.remote

import com.koraextra.app.data.models.*
import com.koraextra.app.data.models.EventsResponse
import com.koraextra.app.data.models.MatchResponse
import com.koraextra.app.data.models.PlayersResponse
import com.koraextra.app.data.models.auth.*
import com.koraextra.app.utily.Injector
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface RetrofitApiService {


    @GET("apigo.php")
    fun getMatchesAsync(
        @Query("go") go: String,
        @Query("api_token") api_token: String = if (Injector.getPreferenceHelper().isLoggedIn) {
            Injector.getPreferenceHelper().token!!
        } else {
            "notRegisted"
        }
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
    fun getLeaguesTableAsync(
        @Query("go") go: String
    ): Deferred<LeagueTableResponse>

    @GET("apigo.php")
    fun getLeaguesMatchesAsync(
        @Query("go") go: String,
        @Query("api_token") api_token: String = if (Injector.getPreferenceHelper().isLoggedIn) {
            Injector.getPreferenceHelper().token!!
        } else {
            "notRegisted"
        }
    ): Deferred<MatchResponse>

    @GET("apigo.php")
    fun getSeasonsAsync(
        @Query("go") go: String
    ): Deferred<SeasonsResponse>

    @POST("api/register")
    fun registerAsync(
        @Body registerBody: RegisterBody
    ): Deferred<RegisterResponse>

    @POST("api/socialRegister")
    fun socialRegisterAsync(
        @Body socialBody: SocialBody
    ): Deferred<SocialResponse>

    @GET("api/viewFavorite")
    fun getFavoriteAsync(
        @Query("api_token") token: String
    ): Deferred<FavoritesResponse>

    @POST("api/addToFavorite")
    fun addToFavoriteAsync(
        @Body favoriteBody: FavoriteBody
    ): Deferred<FavoriteResponse>

    @GET("api/deleteFavorite/{id}")
    fun removeFromFavoriteAsync(
        @Path("id") id: Int,
        @Query("api_token") token: String
    ): Deferred<FavoriteResponse>

    @GET("api/{name}/{status}")
    fun settingAsync(
        @Path("name") name: String,
        @Path("status") status: Int,
        @Query("api_token") token: String = Injector.getPreferenceHelper().token!!
    ): Deferred<SettingResponse>

    @POST("api/doLogin")
    fun loginAsync(
        @Body loginBody: LoginBody
    ): Deferred<LoginResponse>

    @POST("api/resetPassword")
    fun resetPasswordAsync(
        @Body resetPasswordBody: ResetPasswordBody
    ): Deferred<ResetPasswordResponse>

    @GET("api/viewNoti")
    fun notificationsAsync(
        @Query("api_token") api_token: String
    ): Deferred<NotificationsResponse>

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