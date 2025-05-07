package com.example.demophotosearchapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.el.mybasekotlin.data.local.database.AppDatabase
import com.el.mybasekotlin.data.local.database.DatabaseBuilder
import com.example.demophotosearchapp.data.local.AppPreferences
import com.example.demophotosearchapp.data.remote.RetrofitClient
import com.example.demophotosearchapp.data.repository.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by ElChuanmen on 1/13/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setStrictness(Strictness.LENIENT).create()
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase =
        DatabaseBuilder.getInstance(app)
    @Singleton
    @Provides
    fun sharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(AppPreferences.NAME, AppPreferences.MODE)
    @Provides
    fun provideSingleExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }
    @Singleton
    @Provides
    fun provideGloBalStateTest(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }


    @Provides
    @Singleton
    fun provideApiServiceDefault(@ApplicationContext app: Context ): ApiService
            = RetrofitClient.createRetrofitInstance(app, RetrofitClient.TYPE_DOMAIN_API_DEFAULT).create(
        ApiService::class.java)

}