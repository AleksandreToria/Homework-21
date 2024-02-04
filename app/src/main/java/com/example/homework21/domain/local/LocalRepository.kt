package com.example.homework21.domain.local

import com.example.homework21.domain.model.GetItems
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun getItems(): Flow<List<GetItems>>
    suspend fun saveItems(items: List<GetItems>)
    suspend fun getItemsByCategory(category: String): Flow<List<GetItems>>
}