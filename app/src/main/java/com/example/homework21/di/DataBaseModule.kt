package com.example.homework21.di

import android.content.Context
import androidx.room.Room
import com.example.homework21.data.local.database.ItemDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): ItemDataBase =
        Room.databaseBuilder(
            context, ItemDataBase::class.java, "ITEMS_DATABASE"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: ItemDataBase) = db.itemDao()
}