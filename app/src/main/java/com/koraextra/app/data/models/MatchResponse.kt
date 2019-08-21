package com.koraextra.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json


data class MatchResponse(
    @field:Json(name = "api")
    val response: MatchApi?
)

data class MatchApi(
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
    val eventTimestamp: Long?,
    @field:Json(name = "firstHalfStart")
    val firstHalfStart: Long?,
    @field:Json(name = "goalsAwayTeam")
    val goalsAwayTeam: Int? = 0,
    @field:Json(name = "goalsHomeTeam")
    val goalsHomeTeam: Int? = 0,
    @field:Json(name = "homeTeamid")
    val homeTeamid: Int? = 0,
    @field:Json(name = "awayTeamid")
    val awayTeamid: Int? = 0,
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
    val secondHalfStart: Long?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "statusShort")
    val statusShort: String?,
    @field:Json(name = "venue")
    val venue: String?,
    @field:Json(name = "statuskey")
    val statuskey: Int
) : MultiItemEntity {
    override fun getItemType(): Int {
        return statuskey
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
    val teamName: String?,
    @field:Json(name = "fav")
    val favorite: Int?
)

@Entity
data class AwayTeam(
    @PrimaryKey(autoGenerate = true) val Id: Int,
    @field:Json(name = "logo")
    val logo: String?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "team_name")
    val teamName: String?,
    @field:Json(name = "fav")
    val favorite: Int?
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

//match status
//Not Started                           1
//Match Finished                        2
//Time To Be Defined                    3
//Halftime                              4
//Second Half, 2nd Half Started         5
//Extra Time                            6
//Penalty In Progress                   7
//Match Finished After Extra Time       8
//Match Finished After Penalty          9
//Break Time (in Extra Time)            10
//Match Suspended                       11
//Match Interrupted                     12
//Match Postponed                       13
//Match Cancelled                       14
//Match Abandoned                       15
//Technical Loss                        16
//WalkOver                              17
//First Half, Kick Off                  18
//Second half                           19