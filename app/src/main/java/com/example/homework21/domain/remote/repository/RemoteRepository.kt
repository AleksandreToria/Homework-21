package com.example.homework21.domain.remote.repository

import com.example.homework21.data.remote.common.Resource
import com.example.homework21.domain.model.GetItems
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getItems(): Flow<Resource<List<GetItems>>>
}