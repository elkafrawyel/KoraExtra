package com.koraextra.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json


data class EventsResponse(
    @field:Json(name = "api")
    val response: EventsApi?
)

data class EventsApi(
    @field:Json(name = "events")
    val events: List<EventModel?>?,
    @field:Json(name = "firststarted")
    val firststarted: Int?,
    @field:Json(name = "matchfinshed")
    val matchfinshed: Int?,
    @field:Json(name = "results")
    val results: Int?,
    @field:Json(name = "secondstarted")
    val secondstarted: Int?
)

@Entity(tableName = "EventModel")
data class EventModel(
    @field:Json(name = "detail")
    val detail: String?,
    @field:Json(name = "elapsed")
    val elapsed: Int?,
    @field:Json(name = "eventimg")
    val eventimg: String?,
    @field:Json(name = "player")
    val player: String?,
    @field:Json(name = "player_id")
    val playerId: Int?,
    @field:Json(name = "team_id")
    val teamId: Int?,
    @field:Json(name = "teamName")
    val teamName: String?,
    @field:Json(name = "type")
    val type: String?,

    var viewType: Int = 0,
    @field:Json(name = "matchid")
    val fixtureId: Int?,
    @field:Json(name = "hagmahlawy")
    @PrimaryKey val primary: Int?
) : MultiItemEntity {
    override fun getItemType(): Int {
        return viewType
    }
}