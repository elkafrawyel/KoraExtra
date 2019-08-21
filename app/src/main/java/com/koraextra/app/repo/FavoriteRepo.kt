package com.koraextra.app.repo

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.koraextra.app.utily.DataResource
import com.koraextra.app.utily.safeApiCall
import com.koraextra.app.R
import com.koraextra.app.data.models.*
import com.koraextra.app.data.models.auth.RegisterBody
import com.koraextra.app.data.models.auth.RegisterResponse
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.utily.Injector

class FavoriteRepo(
    private val retrofitApiService: RetrofitApiService,
    private val appDatabase: AppDatabase
) {

    //==================================get==============================
    suspend fun getFavorite(token:String): DataResource<FavoritesResponse> {
        return safeApiCall(
            call = { getFavoriteCall(token) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun getFavoriteCall(token:String): DataResource<FavoritesResponse> {
        val response = retrofitApiService.getFavoriteAsync(token).await()
        return DataResource.Success(response)
    }



    //==================================add==============================
    suspend fun addToFavorite(favoriteBody: FavoriteBody): DataResource<FavoriteResponse> {
        return safeApiCall(
            call = { addToFavoriteCall(favoriteBody) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun addToFavoriteCall(favoriteBody: FavoriteBody): DataResource<FavoriteResponse> {
        val response = retrofitApiService.addToFavoriteAsync(favoriteBody).await()
        return DataResource.Success(response)
    }

    //==================================remove==============================
    suspend fun removeToFavorite(id:Int,token:String): DataResource<FavoriteResponse> {
        return safeApiCall(
            call = { removeToFavoriteCall(id,token) },
            errorMessage = Injector.getApplicationContext().getString(R.string.error_general)
        )
    }

    private suspend fun removeToFavoriteCall(id:Int,token:String): DataResource<FavoriteResponse> {
        val response = retrofitApiService.removeFromFavoriteAsync(id,token).await()
        return DataResource.Success(response)
    }

    //==================================update==============================
    fun updateTeam(id:Int,name:String,logo:String,favo:Int){
        val homeAsString = Gson().toJson(AwayTeam(0,logo,id,name,favo))
        val awayAsString = Gson().toJson(HomeTeam(0,logo,id,name,favo))
         val ah = appDatabase.myDao().updateAwayTeam(id,awayAsString)
         val am = appDatabase.myDao().updateHomeTeam(id,homeAsString)
    }
}