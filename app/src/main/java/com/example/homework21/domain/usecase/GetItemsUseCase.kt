package com.example.homework21.domain.usecase

import com.example.homework21.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke() = itemRepository.getItems()
}