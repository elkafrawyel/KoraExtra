package com.koraextra.app.repo

import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.KoraNewsModel
import com.koraextra.app.data.models.MatchVideosResponse
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class NewsRepo(private val retrofitApiService: RetrofitApiService) {
    private val go: String = "/news"

    //    All
    suspend fun getAllNews(): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsAllCall( ) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsAllCall( ): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getAllNewsAsync(go, "yes").await()
        return DataResource.Success(response)
    }

    //    League
    suspend fun getLeagueNews( leagueId: String): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsLeagueCall( leagueId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsLeagueCall( leagueId: String): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getLeagueNewsAsync(go, leagueId).await()
        return DataResource.Success(response)
    }

    //    Match
    suspend fun getMatchNews( MatchId: String): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsMatchCall(MatchId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsMatchCall(MatchId: String): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getMatchNewsAsync(go, MatchId).await()
        return DataResource.Success(response)
    }

    //    Match Videos
    suspend fun getMatchVideos( MatchId: String): DataResource<MatchVideosResponse> {
        return safeApiCall(
            call = { matchVideosCall(MatchId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun matchVideosCall(MatchId: String): DataResource<MatchVideosResponse> {
        val response = retrofitApiService.getMatchVideosAsync("/videosnow", MatchId).await()
        return DataResource.Success(response)
    }

    //    Team
    suspend fun getTeamNews( TeamId: String): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsTeamCall(TeamId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsTeamCall(TeamId: String): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getTeamNewsAsync(go, TeamId).await()
        return DataResource.Success(response)
    }

    //    PlayerModel
    suspend fun getPlayerNews( PlayerId: String): DataResource<List<KoraNewsModel>> {
        return safeApiCall(
            call = { newsPlayerCall(PlayerId) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun newsPlayerCall(PlayerId: String): DataResource<List<KoraNewsModel>> {
        val response = retrofitApiService.getPlayerNewsAsync(go, PlayerId).await()
        return DataResource.Success(response)
    }

}