package com.example.data.local

import androidx.room.*
import com.example.domain.entity.categorized_meals.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_meals")
    fun getAllFavoriteMeals() : Flow<List<Meal>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE idMeal = :id)")
    suspend fun containsId(id: String) : Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)
}