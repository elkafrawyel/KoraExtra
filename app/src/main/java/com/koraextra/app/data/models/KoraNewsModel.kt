package com.koraextra.app.data.models
import com.squareup.moshi.Json


data class KoraNewsModel(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "img")
    val img: String?,
    @field:Json(name = "team_id")
    val teamId: String?,
    @field:Json(name = "player_id")
    val playerId: String?,
    @field:Json(name = "league_id")
    val leagueId: String?,
    @field:Json(name = "created_at")
    val createdAt: String?
)