package com.el.mybasekotlin.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demophotosearchapp.data.local.database.TestUserDao
import com.example.demophotosearchapp.data.model.TestUser

@Database(entities = [TestUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun testUserDao(): TestUserDao
}