package com.koraextra.app.utily

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.koraextra.app.MyApp
import com.koraextra.app.data.storage.local.AppDatabase
import com.koraextra.app.data.storage.local.PreferencesHelper
import com.koraextra.app.data.storage.remote.RetrofitApiService
import com.koraextra.app.repo.*
import com.koraextra.app.utily.Constants.BASE_URL
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object Injector {

    private val coroutinesDispatcherProvider = CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.Default,
        Dispatchers.IO
    )

    fun getApplicationContext() = MyApp.instance
    fun getCoroutinesDispatcherProvider() = coroutinesDispatcherProvider
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun getApiServiceHeader(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")

            if (chain.request().header("Accept-Language") == null) {
                request.addHeader(
                    "Accept-Language",
                    chain.request().header("Accept-Language") ?: getPreferenceHelper().language
                )
            }
            chain.proceed(request.build())
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getApiServiceHeader())
            .addInterceptor(getLoggingInterceptor())
            .build()
    }


    private fun create(baseUrl: String, client: OkHttpClient): RetrofitApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(client)
            .build()

        return retrofit.create(RetrofitApiService::class.java)
    }

    fun getApiService() = create(BASE_URL, getOkHttpClient())

    fun getPreferenceHelper() = PreferencesHelper(getApplicationContext())

    fun getAppDatabase() = AppDatabase.invoke(getApplicationContext())


    // ============================================= Repos ================================================================
    fun getMatchesRepo() = MatchesRepo(getApiService(), getAppDatabase())

    fun getMatchEventsRepo() = MatchEventsRepo(getApiService(), getAppDatabase())
    fun getMatchTopsRepo() = MatchTopsRepo(getApiService())
    fun getLeaguesRepo() = LeaguesRepo(getApiService())
    fun getLeagueTableRepo() = LeagueTableRepo(getApiService())
    fun getSeasonsRepo() = SeasonsRepo(getApiService())
    fun getNewsRepo() = NewsRepo(getApiService())
    fun getLeaguesMatchesRepo() = LeaguesMatchesRepo(getApiService(), getAppDatabase())
    fun getNotificationsRepo() = NotificationsRepo(getApiService(), getPreferenceHelper())

    fun registerRepo() = RegisterRepo(getApiService())
    fun loginRepo() = LoginRepo(getApiService())
    fun resetPasswordRepo() = ResetPasswordRepo(getApiService())
    fun favoriteRepo() = FavoriteRepo(getApiService(), getAppDatabase())

    fun saveFirebaseToken() = SaveFireBaseTokenRepo(getPreferenceHelper())
    fun getSocialRepo() = SocialLoginRepo(getApiService())

    //------------------------------------database-------------------------------
    fun getStoredMatchesRepo() = StoredMatchesRepo(getAppDatabase())

    fun getStoredMatchEventsRepo() = StoredMatchEventsRepo(getAppDatabase())
}