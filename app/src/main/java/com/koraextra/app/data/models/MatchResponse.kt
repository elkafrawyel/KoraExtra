package com.koraextra.app.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.koraextra.app.data.repo.Converters
import com.squareup.moshi.Json


data class MatchResponse(
    @field:Json(name = "api")
    val response: Response?
)

data class Response(
    @field:Json(name = "fixtures")
    val matchModels: List<MatchModel?>?,
    @field:Json(name = "results")
    val count: Int?
)

@Entity(tableName = "MatchModel")
data class MatchModel(
    @field:Json(name = "fixture_id")
    @PrimaryKey val fixtureId: Int?,
    @field:Json(name = "awayTeam")
    val awayTeam: AwayTeam?,
    @field:Json(name = "elapsed")
    val elapsed: Int?,
    @field:Json(name = "event_date")
    val eventDate: String?,
    @field:Json(name = "event_timestamp")
    val eventTimestamp: Int?,
    @field:Json(name = "firstHalfStart")
    val firstHalfStart: Int?,
    @field:Json(name = "goalsAwayTeam")
    val goalsAwayTeam: Int?,
    @field:Json(name = "goalsHomeTeam")
    val goalsHomeTeam: Int?,
    @field:Json(name = "homeTeam")
    val homeTeam: HomeTeam?,
    @field:Json(name = "league_id")
    val leagueId: Int?,
    @field:Json(name = "referee")
    val referee: String?,
    @field:Json(name = "round")
    val round: String?,
    @field:Json(name = "score")
    val score: Score?,
    @field:Json(name = "secondHalfStart")
    val secondHalfStart: Int?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "statusShort")
    val statusShort: String?,
    @field:Json(name = "venue")
    val venue: String?
) : MultiItemEntity {
    override fun getItemType(): Int {
        return 0
    }
}

@Entity
data class HomeTeam(
    @PrimaryKey(autoGenerate = true) val Id: Int,
    @field:Json(name = "logo")
    val logo: String?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "team_name")
    val teamName: String?
)

@Entity
data class AwayTeam(
    @PrimaryKey(autoGenerate = true) val Id: Int,
    @field:Json(name = "logo")
    val logo: String?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "team_name")
    val teamName: String?
)

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val Id: Int,
    @field:Json(name = "extratime")
    val extratime: String?,
    @field:Json(name = "fulltime")
    val fulltime: String?,
    @field:Json(name = "halftime")
    val halftime: String?,
    @field:Json(name = "penalty")
    val penalty: String?
)