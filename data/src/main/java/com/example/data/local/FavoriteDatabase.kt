package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entity.categorized_meals.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao
}