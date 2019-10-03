package com.koraextra.app.data.storage.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koraextra.app.data.models.EventModel
import com.koraextra.app.data.models.MatchModel
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull
import androidx.room.migration.Migration

@Database(
entities = [MatchModel::class, EventModel::class], version = 3
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
            AppDatabase::class.java, "mydatabasea.db")
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE MatchModel add COLUMN idStart Integer NOT NULL DEFAULT 0")
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE EventModel add COLUMN youtube Text")
            }
        }
    }

}