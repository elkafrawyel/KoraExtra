package com.koraextra.app.data.models

import com.squareup.moshi.Json

data class FavoriteResponse(
    @field:Json(name = "ar")
    val ar: String,
    @field:Json(name = "en")
    val en: String,
    @field:Json(name = "status")
    val status: Boolean
)

data class FavoriteBody(
    val teamName: String,
    val teamId: Int,
    val userId: Int,
    val api_token: String,
    val league_id: Int

)


data class FavoritesResponse(
    @field:Json(name = "data")
    val `data`: List<FavoriteModel>,
    @field:Json(name = "status")
    val status: Boolean?
)

data class FavoriteModel(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "teamId")
    val teamId: String?,
    @field:Json(name = "teamName")
    val teamName: String?,
    @field:Json(name = "teamimg")
    val teamLogo: String?
)