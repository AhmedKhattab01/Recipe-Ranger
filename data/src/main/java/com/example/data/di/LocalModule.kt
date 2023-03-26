package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.FavoriteDao
import com.example.data.local.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase): FavoriteDao {
        return favoriteDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteDatabase::class.java,
            "favorite_database"
        ).build()
    }
}