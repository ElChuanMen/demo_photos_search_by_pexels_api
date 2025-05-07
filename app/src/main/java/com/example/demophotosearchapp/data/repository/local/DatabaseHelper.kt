package com.example.demophotosearchapp.data.repository.local


import com.example.demophotosearchapp.data.model.TestUser
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    fun getUsers(): Flow<List<TestUser>>

}