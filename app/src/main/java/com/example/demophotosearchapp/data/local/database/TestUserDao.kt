package com.example.demophotosearchapp.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.demophotosearchapp.data.model.TestUser

@Dao
interface TestUserDao {
    @Query("SELECT * FROM testuser")
    fun getAll(): List<TestUser>

    @Insert
    fun insertAll(users: List<TestUser>)

    @Delete
    fun delete(user: TestUser)
}