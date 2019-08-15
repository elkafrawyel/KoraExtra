package com.koraextra.app.data.models

import com.squareup.moshi.Json


data class PlayersResponse(
    @field:Json(name = "api")
    val api: TopsApi?
)

data class TopsApi(
    @field:Json(name = "players")
    val players: List<PlayerModel>?,
    @field:Json(name = "results")
    val results: Int?
)

data class PlayerModel(
    @field:Json(name = "captain")
    val captain: String?,
    @field:Json(name = "nationality")
    val nationality: String?,
    @field:Json(name = "cards")
    val cards: Cards?,
    @field:Json(name = "dribbles")
    val dribbles: Dribbles?,
    @field:Json(name = "duels")
    val duels: Duels?,
    @field:Json(name = "age")
    val age: Int?,
    @field:Json(name = "event_id")
    val eventId: Int?,
    @field:Json(name = "fouls")
    val fouls: Fouls?,
    @field:Json(name = "goals")
    val goals: Goals?,
    @field:Json(name = "minutes_played")
    val minutesPlayed: Int?,
    @field:Json(name = "number")
    val number: Int?,
    @field:Json(name = "offsides")
    val offsides: Any?,
    @field:Json(name = "passes")
    val passes: Passes?,
    @field:Json(name = "penalty")
    val penalty: Penalty?,
    @field:Json(name = "player_id")
    val playerId: Int?,
    @field:Json(name = "player_name")
    val playerName: String,
    @field:Json(name = "position")
    val position: String?,
    @field:Json(name = "rating")
    val rating: String?,
    @field:Json(name = "shots")
    val shots: Shots?,
    @field:Json(name = "substitute")
    val substitute: String?,
    @field:Json(name = "tackles")
    val tackles: Tackles?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "league")
    val league: String?,
    @field:Json(name = "team_name")
    val teamName: String?,
    @field:Json(name = "updateAt")
    val updateAt: Int?,
    @field:Json(name = "playerimage")
    val playerimage: String?
) {
    override fun toString(): String {
        return league!!
    }
}

data class Goals(
    @field:Json(name = "assists")
    val assists: Int?,
    @field:Json(name = "conceded")
    val conceded: Int?,
    @field:Json(name = "total")
    val total: Int?
)

data class Shots(
    @field:Json(name = "on")
    val on: Int?,
    @field:Json(name = "total")
    val total: Int?
)

data class Duels(
    @field:Json(name = "total")
    val total: Int?,
    @field:Json(name = "won")
    val won: Int?
)

data class Penalty(
    @field:Json(name = "commited")
    val commited: Int?,
    @field:Json(name = "missed")
    val missed: Int?,
    @field:Json(name = "saved")
    val saved: Int?,
    @field:Json(name = "success")
    val success: Int?,
    @field:Json(name = "won")
    val won: Int?
)

data class Cards(
    @field:Json(name = "red")
    val red: Int?,
    @field:Json(name = "yellow")
    val yellow: Int?
)

data class Passes(
    @field:Json(name = "accuracy")
    val accuracy: Int?,
    @field:Json(name = "key")
    val key: Int?,
    @field:Json(name = "total")
    val total: Int?
)

data class Tackles(
    @field:Json(name = "blocks")
    val blocks: Int?,
    @field:Json(name = "interceptions")
    val interceptions: Int?,
    @field:Json(name = "total")
    val total: Int?
)

data class Fouls(
    @field:Json(name = "committed")
    val committed: Int?,
    @field:Json(name = "drawn")
    val drawn: Int?
)

data class Dribbles(
    @field:Json(name = "attempts")
    val attempts: Int?,
    @field:Json(name = "past")
    val past: Int?,
    @field:Json(name = "success")
    val success: Int?
)