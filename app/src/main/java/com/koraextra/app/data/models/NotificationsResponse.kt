package com.koraextra.app.data.models

import com.squareup.moshi.Json

data class NotificationsResponse(
    @field:Json(name = "status")
    val status: Boolean,
    @field:Json(name = "data")
    val notifications: List<NotificationModel>
)

data class NotificationModel(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "notiText")
    val notiText: String?,
    @field:Json(name = "notimg")
    val notimg: String?,
    @field:Json(name = "userId")
    val userId: String?,
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "updated_at")
    val updatedAt: String?
)

data class FireTokenRespons(
    @field:Json(name = "status")
    val status: String?
)