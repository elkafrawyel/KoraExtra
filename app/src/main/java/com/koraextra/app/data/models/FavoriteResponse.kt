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
    val api_token: String

)