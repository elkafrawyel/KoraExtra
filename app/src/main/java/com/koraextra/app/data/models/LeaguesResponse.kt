package com.koraextra.app.data.models

import com.squareup.moshi.Json


data class LeaguesResponse(
    @field:Json(name = "api")
    val leaguesApi: LeaguesApi?
)

data class LeaguesApi(
    @field:Json(name = "results")
    val results: Int,
    @field:Json(name = "leagues")
    val leagues: List<LeagueModel>
)

data class LeagueModel(
    @field:Json(name = "league_id")
    val leagueId: Int?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "type")
    val type: String?,
    @field:Json(name = "country")
    val country: String?,
    @field:Json(name = "country_code")
    val countryCode: String?,
    @field:Json(name = "season")
    val season: Int?,
    @field:Json(name = "season_start")
    val seasonStart: String?,
    @field:Json(name = "season_end")
    val seasonEnd: String?,
    @field:Json(name = "logo")
    val logo: Any?,
    @field:Json(name = "flag")
    val flag: String?,
    @field:Json(name = "standings")
    val standings: Int?,
    @field:Json(name = "is_current")
    val isCurrent: Int?
)