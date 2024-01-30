package com.example.homework21.data.repository

import com.example.homework21.data.common.HandleResponse
import com.example.homework21.data.common.Resource
import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.local.mapper.toData
import com.example.homework21.data.local.mapper.toDomain
import com.example.homework21.data.remote.mapper.item.toDomain
import com.example.homework21.data.remote.service.ItemService
import com.example.homework21.data.util.NetworkStatus
import com.example.homework21.domain.model.GetItems
import com.example.homework21.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemService: ItemService,
    private val handleResponse: HandleResponse,
    private val networkStatus: NetworkStatus,
    private val itemDao: ItemDao
) : ItemRepository {
    override suspend fun getItems(): Flow<Resource<List<GetItems>>> = flow {
        if (networkStatus.isConnected()) {
            handleResponse.safeApiCall { itemService.getItems() }
                .map { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val domainItems = resource.data.map { apiItem ->
                                val domainItem = apiItem.toDomain()
                                itemDao.insertItem(domainItem.toData())
                                domainItem
                            }
                            Resource.Success(domainItems)
                        }

                        is Resource.Error -> Resource.Error(resource.errorMessage)
                        is Resource.Loading -> Resource.Loading(resource.loading)
                    }
                }.collect { emit(it) }
        } else {
            emit(Resource.Loading(true))
            itemDao.getAllItems().collect { entities ->
                val itemsList = entities.map { it.toDomain() }
                emit(Resource.Success(itemsList))
                emit(Resource.Loading(false))
            }
        }
    }
}