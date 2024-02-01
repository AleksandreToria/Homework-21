package com.example.homework21.data.remote.datasource

import com.example.homework21.data.remote.common.HandleResponse
import com.example.homework21.data.remote.common.Resource
import com.example.homework21.data.remote.mapper.base.asResource
import com.example.homework21.data.remote.mapper.item.toDomain
import com.example.homework21.data.remote.service.ItemService
import com.example.homework21.domain.model.GetItems
import com.example.homework21.domain.remote.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val itemService: ItemService,
    private val handleResponse: HandleResponse
) : RemoteRepository {

    override suspend fun getItems(): Flow<Resource<List<GetItems>>> {
        return handleResponse.safeApiCall {
            itemService.getItems()
        }.asResource { it ->
            it.map {
                it.toDomain()
            }
        }
    }
}