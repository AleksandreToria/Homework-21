package com.example.homework21.di

import com.example.homework21.data.common.HandleResponse
import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.remote.service.ItemService
import com.example.homework21.data.repository.ItemRepositoryImpl
import com.example.homework21.data.util.NetworkStatus
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
    fun provideItemRepository(
        itemService: ItemService,
        handleResponse: HandleResponse,
        networkStatus: NetworkStatus,
        itemDao: ItemDao
    ): ItemRepository {
        return ItemRepositoryImpl(
            itemService = itemService,
            handleResponse = handleResponse,
            networkStatus = networkStatus,
            itemDao = itemDao
        )
    }
}