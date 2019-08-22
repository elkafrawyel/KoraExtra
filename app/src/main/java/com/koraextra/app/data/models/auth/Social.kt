package com.koraextra.app.data.models.auth

import com.squareup.moshi.Json


data class SocialResponse(
    @field:Json(name = "status")
    val status: Boolean?,
    @field:Json(name = "en")
    val en: String?,
    @field:Json(name = "ar")
    val ar: String?
)


data class SocialBody(
    var name: String,
    var email: String,
    var api_token_rule: String,
    var api_token: String,
    var firebasetoken: String
)