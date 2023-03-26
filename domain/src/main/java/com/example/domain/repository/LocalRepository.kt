package com.example.domain.repository

import com.example.domain.entity.categorized_meals.Meal
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllFavoriteMeals() : Flow<List<Meal>>

    suspend fun insertMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)

    suspend fun containsId(id: String) : Boolean
}