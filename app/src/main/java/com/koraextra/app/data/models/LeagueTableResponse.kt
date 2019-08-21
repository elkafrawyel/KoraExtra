package com.koraextra.app.data.models

import com.chad.library.adapter.base.entity.SectionEntity
import com.squareup.moshi.Json
import android.provider.MediaStore.Video




data class LeagueTableResponse(
    @field:Json(name = "api")
    val api: Api?
)

data class Api(
    @field:Json(name = "results")
    val results: Int?,
    @field:Json(name = "standings")
    val groupTable: List<GroupTable>
)

data class GroupTable(
    @field:Json(name = "groupname")
    val groupname: String,
    @field:Json(name = "group")
    val group: List<GroupTeam>
)

data class GroupTeam(
    @field:Json(name = "rank")
    val rank: Int?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "teamName")
    val teamName: String?,
    @field:Json(name = "logo")
    val logo: String?,
    @field:Json(name = "group")
    val group: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "all")
    val all: All?,
    @field:Json(name = "home")
    val home: Home?,
    @field:Json(name = "away")
    val away: Away?,
    @field:Json(name = "goalsDiff")
    val goalsDiff: Int?,
    @field:Json(name = "points")
    val points: Int?,
    @field:Json(name = "lastUpdate")
    val lastUpdate: String?,
    @field:Json(name = "forme")
    val forme: Any?
)

data class Away(
    @field:Json(name = "matchsPlayed")
    val matchsPlayed: Int?,
    @field:Json(name = "win")
    val win: Int?,
    @field:Json(name = "draw")
    val draw: Int?,
    @field:Json(name = "lose")
    val lose: Int?,
    @field:Json(name = "goalsFor")
    val goalsFor: Int?,
    @field:Json(name = "goalsAgainst")
    val goalsAgainst: Int?
)

data class Home(
    @field:Json(name = "matchsPlayed")
    val matchsPlayed: Int?,
    @field:Json(name = "win")
    val win: Int?,
    @field:Json(name = "draw")
    val draw: Int?,
    @field:Json(name = "lose")
    val lose: Int?,
    @field:Json(name = "goalsFor")
    val goalsFor: Int?,
    @field:Json(name = "goalsAgainst")
    val goalsAgainst: Int?
)

data class All(
    @Json(name = "matchsPlayed")
    val matchsPlayed: Int?,
    @Json(name = "win")
    val win: Int?,
    @Json(name = "draw")
    val draw: Int?,
    @Json(name = "lose")
    val lose: Int?,
    @Json(name = "goalsFor")
    val goalsFor: Int?,
    @Json(name = "goalsAgainst")
    val goalsAgainst: Int?
)

class LeagueTableModel : SectionEntity<GroupTeam> {
    var isMore: Boolean = false

    constructor(isHeader: Boolean, headerText: String, isMore: Boolean) : super(isHeader, headerText) {
        this.isMore = isMore
    }

    constructor(item: GroupTeam) : super(item)
}
