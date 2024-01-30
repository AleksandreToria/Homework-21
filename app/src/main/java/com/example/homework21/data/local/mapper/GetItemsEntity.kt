package com.example.homework21.data.local.mapper

import com.example.homework21.data.local.model.ItemEntity
import com.example.homework21.domain.model.GetItems

fun GetItems.toData(): ItemEntity {
    return ItemEntity(
        id = id,
        price = price,
        cover = cover,
        title = title,
        favorite = favorite
    )
}

fun ItemEntity.toDomain(): GetItems {
    return GetItems(
        id = id,
        price = price,
        cover = cover,
        title = title,
        favorite = favorite
    )
}