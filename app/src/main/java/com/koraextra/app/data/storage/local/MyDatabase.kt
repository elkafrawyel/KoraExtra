package com.koraextra.app.data.storage.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koraextra.app.data.models.AwayTeam
//import com.koraextra.app.data.models.HomeTeam
import com.koraextra.app.data.models.MatchModel
import com.koraextra.app.data.models.Score

@Database(
entities = [MatchModel::class], version = 1
)@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "mydatabasea.db"
        ).build()
    }
}