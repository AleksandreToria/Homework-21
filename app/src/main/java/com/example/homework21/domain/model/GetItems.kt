package com.example.homework21.domain.model

data class GetItems(
    val id: Int,
    val cover: String,
    val price: String,
    val title: String,
    val favorite: Boolean,
    val category: String
)
