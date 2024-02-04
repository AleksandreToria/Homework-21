package com.example.homework21.data.local.datasource

import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.local.mapper.toData
import com.example.homework21.data.local.mapper.toDomain
import com.example.homework21.domain.local.LocalRepository
import com.example.homework21.domain.model.GetItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val itemDao: ItemDao
) : LocalRepository {
    override suspend fun getItems(): Flow<List<GetItems>> = withContext(Dispatchers.IO) {
        itemDao.getAllItems().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun saveItems(items: List<GetItems>) = withContext(Dispatchers.IO) {
        items.forEach {
            itemDao.insertItem(it.toData())
        }
    }

    override suspend fun getItemsByCategory(category: String) = withContext(Dispatchers.IO) {
        itemDao.getItemByCategory(category).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}