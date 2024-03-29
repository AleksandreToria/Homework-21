package com.example.homework21.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "cover")
    val cover: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String? = null
)