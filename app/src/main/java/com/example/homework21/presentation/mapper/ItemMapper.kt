package com.example.homework21.presentation.mapper

import com.example.homework21.domain.model.GetItems
import com.example.homework21.presentation.model.Items

fun GetItems.toPresenter() =
    Items(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite,
        category = category
    )