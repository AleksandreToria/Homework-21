package com.example.homework21.domain.usecase

import com.example.homework21.data.remote.common.Resource
import com.example.homework21.domain.model.GetItems
import com.example.homework21.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsByIdUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(category: String): Flow<Resource<List<GetItems>>> = itemRepository.getItemsByCategory(category = category)
}