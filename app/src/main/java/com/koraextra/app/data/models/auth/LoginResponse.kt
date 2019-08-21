package com.koraextra.app.data.models.auth

import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "status")
    val status: Boolean?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "token")
    val token: String?,
    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "ar")
    val ar: String?,
    @field:Json(name = "en")
    val en: String?
)

data class LoginBody(
    private var email: String,
    private var password: String,
    private var firebasetoken: String
)