package com.el.mybasekotlin.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.demophotosearchapp.data.constant.Constant.DATABASE_NAME

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = buildRoomDB(context)
                }
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).addMigrations(MIGRATION_1_2).allowMainThreadQueries().fallbackToDestructiveMigration().build()

}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `Order` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `amount` INTEGER NOT NULL
            )
        """.trimIndent())
    }
}