package com.koraextra.app.data.models.auth
import com.squareup.moshi.Json


data class ResetPasswordResponse(
    @field:Json(name = "status")
    val status: Boolean?,
    @field:Json(name = "en")
    val en: String?,
    @field:Json(name = "ar")
    val ar: String?
)

class ResetPasswordBody(
    val email: String
)