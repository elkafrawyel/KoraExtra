package com.koraextra.app.data.storage.local

import androidx.room.TypeConverter
import com.koraextra.app.data.models.AwayTeam
import com.koraextra.app.data.models.HomeTeam
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.koraextra.app.data.models.Score


class Converters {

    companion object {
        var gson = Gson()
    }

    @TypeConverter
    fun getHomeTeamFromString(homeTeamString: String?): HomeTeam {
        val listType = object : TypeToken<HomeTeam>() {}.type
        return gson.fromJson<HomeTeam>(homeTeamString, listType)
    }

    @TypeConverter
    fun setHomeTeamAsString(objects: HomeTeam): String {
        return gson.toJson(objects)
    }



    @TypeConverter
    fun stringToAwayTeam(data: String?): AwayTeam {

        val listType = object : TypeToken<AwayTeam>() {

        }.type

        return gson.fromJson<AwayTeam>(data, listType)
    }

    @TypeConverter
    fun AwayTeamListToString(someObjects: AwayTeam): String {
        return gson.toJson(someObjects)
    }



    @TypeConverter
    fun stringToScore(data: String?): Score {

        val listType = object : TypeToken<Score>() {

        }.type

        return gson.fromJson<Score>(data, listType)
    }

    @TypeConverter
    fun ScoreListToString(someObjects: Score): String {
        return gson.toJson(someObjects)
    }







}