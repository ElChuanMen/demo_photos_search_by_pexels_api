package com.example.demophotosearchapp.di

import com.example.demophotosearchapp.data.repository.local.DatabaseHelper
import com.example.demophotosearchapp.data.repository.local.DatabaseHelperImpl
import com.example.demophotosearchapp.data.repository.network.ApiHelper
import com.example.demophotosearchapp.data.repository.network.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface ViewModelModule {
    @Binds
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper

    @Binds
    fun provideDbHelper(dbHelper: DatabaseHelperImpl): DatabaseHelper
}