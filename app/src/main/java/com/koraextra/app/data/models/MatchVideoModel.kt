package com.koraextra.app.data.models

import com.squareup.moshi.Json

data class MatchVideoModel(
    @field:Json(name = "video")
    val video: String?,
    @field:Json(name = "img")
    val img: String?,
    @field:Json(name = "title")
    val title: String?
)