package com.example.data.repository

import com.example.data.remote.ApiService
import com.example.domain.entity.areas.AreasResponse
import com.example.domain.entity.categorized_meals.CategorizedMealsResponse
import com.example.domain.entity.category.CategoryResponse
import com.example.domain.entity.meal.MealResponse
import com.example.domain.entity.random_meal.RandomMealResponse
import com.example.domain.repository.NetworkRepository
import retrofit2.Response

class NetworkRepoImpl(private val apiService: ApiService) : NetworkRepository {
    override suspend fun getCategoryFromNetwork(): Response<CategoryResponse> {
        return apiService.getCategoriesFromNetwork()
    }

    override suspend fun getRandomMealFromNetwork(): Response<RandomMealResponse> {
        return apiService.getRandomMealFromNetwork()
    }

    override suspend fun getAreasFromNetwork(): Response<AreasResponse> {
        return apiService.getAreasFromNetwork()
    }

    override suspend fun getMealsByCategoryFromNetwork(category: String): Response<CategorizedMealsResponse> {
        return apiService.getMealsByCategoryFromNetwork(category)
    }

    override suspend fun getMealsByAreaFromNetwork(area: String): Response<CategorizedMealsResponse> {
        return apiService.getMealsByAreaFromNetwork(area)
    }

    override suspend fun getMealDetailsFromNetwork(id: String): Response<MealResponse> {
        return apiService.getMealDetailsFromNetwork(id)
    }

    override suspend fun getMealsBySearch(strSearch: String): Response<MealResponse> {
        return apiService.getMealsBySearch(strSearch)
    }
}