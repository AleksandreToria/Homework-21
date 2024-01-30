package com.example.homework21.domain.repository

import com.example.homework21.data.common.Resource
import com.example.homework21.domain.model.GetItems
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItems(): Flow<Resource<List<GetItems>>>
}