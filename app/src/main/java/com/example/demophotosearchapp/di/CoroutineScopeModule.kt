package com.example.demophotosearchapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IOScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainScope


@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @DefaultScope
    fun provideDefaultScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @IOScope
    fun provideIOScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @MainScope
    fun provideMainScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}