package com.example.homework21.di

import com.example.homework21.data.remote.common.HandleResponse
import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.local.datasource.LocalDataSource
import com.example.homework21.data.remote.datasource.RemoteDataSource
import com.example.homework21.data.remote.service.ItemService
import com.example.homework21.data.repository.ItemRepositoryImpl
import com.example.homework21.data.util.NetworkStatus
import com.example.homework21.domain.local.LocalRepository
import com.example.homework21.domain.remote.repository.RemoteRepository
import com.example.homework21.domain.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteRepository(
        itemService: ItemService,
        handleResponse: HandleResponse,
    ): RemoteRepository {
        return RemoteDataSource(
            itemService = itemService,
            handleResponse = handleResponse,
        )
    }

    @Singleton
    @Provides
    fun provideLocalRepository(
        itemDao: ItemDao
    ): LocalRepository {
        return LocalDataSource(
            itemDao = itemDao
        )
    }

    @Singleton
    @Provides
    fun provideItemRepository(
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository,
        networkStatus: NetworkStatus
    ): ItemRepository {
        return ItemRepositoryImpl(
            remoteRepository = remoteRepository,
            localRepository = localRepository,
            networkStatus = networkStatus
        )
    }
}