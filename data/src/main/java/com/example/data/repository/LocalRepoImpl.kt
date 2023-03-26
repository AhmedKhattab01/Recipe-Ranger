package com.example.data.repository

import com.example.data.local.FavoriteDao
import com.example.domain.entity.categorized_meals.Meal
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepoImpl(private val favoriteDao: FavoriteDao) : LocalRepository {
    override fun getAllFavoriteMeals(): Flow<List<Meal>> {
        return favoriteDao.getAllFavoriteMeals()
    }

    override suspend fun insertMeal(meal: Meal) {
        favoriteDao.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        favoriteDao.deleteMeal(meal)
    }

    override suspend fun containsId(id: String): Boolean {
        return favoriteDao.containsId(id)
    }
}