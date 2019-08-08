package com.koraextra.app.data.models
import com.squareup.moshi.Json


data class SeasonsResponse(
    @field:Json(name = "api")
    val seasons: Seasons
)

data class Seasons(
    @field:Json(name = "results")
    val results: Int?,
    @field:Json(name = "seasons")
    val seasons: List<Int>
)