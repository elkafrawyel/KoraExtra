package com.koraextra.app.data.models
import com.squareup.moshi.Json


data class SettingResponse(
    @field:Json(name = "ar")
    val ar: String,
    @field:Json(name = "en")
    val en: String,
    @field:Json(name = "status")
    val status: Boolean
)