package com.example.domain.repository

import com.example.domain.entity.areas.AreasResponse
import com.example.domain.entity.categorized_meals.CategorizedMealsResponse
import com.example.domain.entity.category.CategoryResponse
import com.example.domain.entity.meal.MealResponse
import com.example.domain.entity.random_meal.RandomMealResponse
import retrofit2.Response
import retrofit2.http.Query

interface NetworkRepository {
    suspend fun getCategoryFromNetwork(): Response<CategoryResponse>

    suspend fun getRandomMealFromNetwork(): Response<RandomMealResponse>

    suspend fun getAreasFromNetwork(): Response<AreasResponse>

    suspend fun getMealsByCategoryFromNetwork(category: String): Response<CategorizedMealsResponse>

    suspend fun getMealDetailsFromNetwork(@Query("i")  id :String) : Response<MealResponse>
}