package com.example.demophotosearchapp.data.repository.local

import com.el.mybasekotlin.data.local.database.AppDatabase
import com.example.demophotosearchapp.data.model.TestUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) :
    DatabaseHelper {
    override fun getUsers(): Flow<List<TestUser>> = flow { emit(appDatabase.testUserDao().getAll()) }
}