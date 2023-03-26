package com.example.data.remote

import com.example.domain.entity.areas.AreasResponse
import com.example.domain.entity.categorized_meals.CategorizedMealsResponse
import com.example.domain.entity.category.CategoryResponse
import com.example.domain.entity.meal.MealResponse
import com.example.domain.entity.random_meal.RandomMealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategoriesFromNetwork() : Response<CategoryResponse>

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMealFromNetwork() : Response<RandomMealResponse>

    @GET("/api/json/v1/1/list.php")
    suspend fun getAreasFromNetwork(@Query("a") area : String = "list") : Response<AreasResponse>

    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealsByCategoryFromNetwork(@Query("c")  category :String) : Response<CategorizedMealsResponse>

    @GET("/api/json/v1/1/filter.php")
    suspend fun getMealsByAreaFromNetwork(@Query("a")  area :String) : Response<CategorizedMealsResponse>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getMealDetailsFromNetwork(@Query("i")  id :String) : Response<MealResponse>

    @GET("/api/json/v1/1/search.php")
    suspend fun getMealsBySearch(@Query("s")  strSearch :String) : Response<MealResponse>
}