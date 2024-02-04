package com.example.homework21.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.homework21.data.local.dao.ItemDao
import com.example.homework21.data.local.model.ItemEntity

@Database(
    entities = [ItemEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class ItemDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}