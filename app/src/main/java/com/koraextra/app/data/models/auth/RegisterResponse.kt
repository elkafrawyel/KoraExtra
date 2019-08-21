package com.koraextra.app.data.models.auth

import com.squareup.moshi.Json

data class RegisterResponse(
    @field:Json(name = "status")
    val status: Boolean?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "api_token")
    val token: String?,
    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "created_at")
    val createdAt: CreatedAt?,
    @field:Json(name = "updated_at")
    val updatedAt: UpdatedAt?,
    @field:Json(name = "ar")
    val ar: Ar?,
    @field:Json(name = "en")
    val en: En?
)

data class En(
    @Json(name = "name")
    val name: String?,
    @Json(name = "email")
    val email: String?
)

data class Ar(
    @Json(name = "name")
    val name: String?,
    @Json(name = "email")
    val email: String?
)

data class CreatedAt(
    @field:Json(name = "date")
    val date: String?,
    @field:Json(name = "timezone_type")
    val timezoneType: Int?,
    @field:Json(name = "timezone")
    val timezone: String?
)

data class UpdatedAt(
    @field:Json(name = "date")
    val date: String?,
    @field:Json(name = "timezone_type")
    val timezoneType: Int?,
    @field:Json(name = "timezone")
    val timezone: String?
)

data class RegisterBody(
    private var name: String,
    private var email: String,
    private var password: String,
    private var confirmPassword: String,
    private var firebasetoken: String
)