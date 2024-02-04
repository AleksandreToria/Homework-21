package com.example.homework21.data.repository

import com.example.homework21.data.remote.common.Resource
import com.example.homework21.data.util.NetworkStatus
import com.example.homework21.domain.local.LocalRepository
import com.example.homework21.domain.model.GetItems
import com.example.homework21.domain.remote.repository.RemoteRepository
import com.example.homework21.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkStatus: NetworkStatus
) : ItemRepository {
    override suspend fun getItems(): Flow<Resource<List<GetItems>>> = flow {
        networkStatus.networkStatus.collect { isConnected ->
            if (isConnected) {
                remoteRepository.getItems().collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            localRepository.saveItems(resource.data)
                            emit(resource)
                        }

                        is Resource.Error -> {
                            emit(resource)
                        }

                        is Resource.Loading -> {
                            emit(resource)
                        }
                    }
                }
            } else {
                localRepository.getItems().collect { items ->
                    emit(Resource.Success(items))
                }
            }
        }
    }

    override suspend fun getItemsByCategory(category: String): Flow<Resource<List<GetItems>>> = flow {
        emit(Resource.Loading(loading = true))
        try {
            val items = localRepository.getItemsByCategory(category).first()
            if (items.isNotEmpty()) {
                emit(Resource.Success(items))
            } else {
                emit(Resource.Error("No items found for category $category"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch items by category: ${e.message}"))
        }
    }
}