package com.example.homework21.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.local.model.ItemEntity

@Database(entities = [ItemEntity::class], version = 1)
abstract class ItemDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}