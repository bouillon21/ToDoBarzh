package com.example.todobarzh.di

import com.example.todobarzh.data.source.NetworkDataSource
import com.example.todobarzh.data.repository.TodoItemsRepositoryImpl
import com.example.todobarzh.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideNetworkDataSource() = NetworkDataSource.create()

    @Singleton
    @Provides
    fun provideTodoItemsRepository(
        networkProvider: NetworkDataSource
    ): TodoItemsRepository = TodoItemsRepositoryImpl(networkProvider)
}