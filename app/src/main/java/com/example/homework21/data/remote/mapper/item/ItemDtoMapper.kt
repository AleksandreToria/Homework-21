package com.example.homework21.data.remote.mapper.item

import com.example.homework21.data.remote.model.ItemDto
import com.example.homework21.domain.model.GetItems

fun ItemDto.toDomain() =
    GetItems(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )