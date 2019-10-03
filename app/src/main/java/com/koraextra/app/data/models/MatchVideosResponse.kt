package com.koraextra.app.data.models


import com.koraextra.app.ui.mainActivity.match.videos.AdapterVideos
import com.squareup.moshi.Json

data class MatchVideosResponse(
    @field:Json(name = "data")
    val videos: List<MatchVideoModel>
)