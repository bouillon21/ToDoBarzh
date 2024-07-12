package com.example.todobarzh.di

import android.content.Context
import androidx.room.Room
import com.example.todobarzh.data.repository.TodoItemsRepositoryImpl
import com.example.todobarzh.data.room.TodoDao
import com.example.todobarzh.data.room.TodoDatabase
import com.example.todobarzh.data.source.NetworkDataSource
import com.example.todobarzh.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
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
        networkProvider: NetworkDataSource,
        localProvider: TodoDao,
    ): TodoItemsRepository = TodoItemsRepositoryImpl(networkProvider, localProvider)

    @Singleton
    @Provides
    fun provideTaskDataBaseDao(
        @ApplicationContext context: Context,
    ): TodoDao {
        val dataBase = Room.databaseBuilder(
            context = context,
            klass = TodoDatabase::class.java,
            name = "todo.db"
        )
            .setQueryCoroutineContext(Dispatchers.IO + CoroutineName("Room coroutines"))
            .build()
        return dataBase.dao
    }

}