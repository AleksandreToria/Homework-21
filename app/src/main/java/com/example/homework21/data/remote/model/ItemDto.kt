package com.example.homework21.data.remote.model

data class ItemDto(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    val category: String
)
